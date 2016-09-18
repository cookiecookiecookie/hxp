package com.coshine.batsys.spring.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class HandlerExceptionViewResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof HandlerExceptionView) {
            ModelAndView mav = ((HandlerExceptionView) ex).getModelAndView();
            Integer statusCode = determineStatusCode(request, mav.getViewName());
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            return mav;
        }
        return super.doResolveException(request, response, handler, ex);
    }

}
