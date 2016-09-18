package com.coshine.batsys.web;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.coshine.batsys.aop.WebHandleInterceptor;
import com.coshine.batsys.json.Jackson;
import com.coshine.batsys.spring.mvc.HandlerExceptionView;
import com.coshine.batsys.util.Requests;
import com.coshine.batsys.util.Result;

@Component
public class SecurityCheckInterceptor implements WebHandleInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityCheckInterceptor.class);
	
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void preHandle(MethodSignature methodSignature) throws Exception {
		Method method = methodSignature.getMethod();
		WebAction wa = method.getAnnotation(WebAction.class);
		if (wa == null) {
			return;
		}

		Permission permission = wa.value();
		if (LOG.isInfoEnabled()) {
			LOG.info("客户端：[{}] 功能：[{}] 描述：[{}]", Requests.getRequest().getRemoteHost(), permission.name(), permission.description());
		}
		
		if (WebContext.checkPermission(permission.name())) {
			return;
		}
		
		String loggedUser = WebContext.loggedUser();
		if (loggedUser == null) {
			errorHandle("请登录", "PLEASE_LOGIN", "redirect:/login");
		} else {
			errorHandle("授权失败", "AUTHORIZATION_FAILURE", "redirect:/auth_fail");
		}
	}
	
	private void errorHandle(String errorMessage, String errorCode, String errorView) {
		LOG.info(errorMessage);
		if (Requests.isAjaxRequest()) {
			Result<String> result = Result.create(errorCode, errorMessage);
			throw new HandlerExceptionView(errorMessage, new View() {
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
			throw new HandlerExceptionView(errorMessage, errorView);
		}
	}

}
