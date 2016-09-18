package com.coshine.batsys.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.coshine.batsys.json.Jackson;
import com.coshine.batsys.spring.mvc.HandlerExceptionView;
import com.coshine.batsys.util.Requests;
import com.coshine.batsys.util.Result;

/**
 * Created by json on 2015/7/11.
 */
@Aspect
public class WebHandleInterceptorAdvisor implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(WebHandleInterceptorAdvisor.class);

    public static final String ERROR_PAGE = "error_page";

    private WebHandleInterceptor[] interceptors;

//    @Around("execution(* com.coshine.batsys.web..*Controller.*(..))")
    @Around("@annotation(com.coshine.batsys.web.WebAction)")
    public Object aroundHandle(ProceedingJoinPoint joinPoint) {
        LOG.info("joinPoint: {}", joinPoint);
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        try {
            if (interceptors != null) {
                for (WebHandleInterceptor wmi : interceptors) {
                	wmi.preHandle(ms);
                }
            }
            return joinPoint.proceed();
        } catch (HandlerExceptionView e) {
            throw e;
        } catch (Throwable t) {
            LOG.error(t.getMessage(), t);
            if (Requests.isAjaxRequest()) {
            	String message = "服务器繁忙，请稍后再试！";
    			Result<String> result = Result.create("METHOD_PROCEED_FAILURE", message);
    			throw new HandlerExceptionView(message, new View() {
    				@Override
    				public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
    						throws Exception {
    					Jackson.writeJsonToStream(response.getOutputStream(), result);
    				}

    				@Override
    				public String getContentType() {
    					return MappingJackson2JsonView.DEFAULT_CONTENT_TYPE;
    				}
    			});
    		} else {
    			String stackTrace = null;
                try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
                    t.printStackTrace(pw);
                    stackTrace = sw.toString();
                } catch (IOException e) {
                    LOG.warn(t.getMessage(), t);
                }
				Map<String, String> model = new HashMap<>(4);
				model.put("error", t.getMessage());
				model.put("status", "500");
				model.put("timestamp", LocalDateTime.now().toString());
				model.put("message", stackTrace);
				throw new HandlerExceptionView(t.getMessage(), ERROR_PAGE, model);
    		}
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<?, WebHandleInterceptor> vr = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, WebHandleInterceptor.class, true, false);
        LOG.info("Interceptors: {}", vr);
        if (!vr.isEmpty()) {
            this.interceptors = new WebHandleInterceptor[vr.size()];
            vr.values().toArray(this.interceptors);
            OrderComparator.sort(this.interceptors);
        }
    }
}
