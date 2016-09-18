package com.coshine.batsys.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by jia on 2015/7/26.
 */
public class Requests {

    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpSession getSession(boolean isCreate) {
        return getRequest().getSession(isCreate);
    }
    
    public static ServletContext getServletContext() {
    	return getSession().getServletContext();
    }

    public static boolean isAjaxRequest() {
    	return getRequest().getHeader("X-REQUESTED-WITH") == null ? false : true;
    }
}
