package com.coshine.batsys.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class WebsiteListener implements HttpSessionListener, ServletContextListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebsiteListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Session [{}] created.", se.getSession().getId());
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Session [{}] destroyed.", se.getSession().getId());
		}
//		WebContext.removeCurrentStaff(se.getSession());
	}

	@Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        String path = sc.getContextPath();
        if (StringUtils.isEmpty(path) || path.equals("/")) {
            path = "";
        }
        sc.setAttribute("ctx", path);
        sc.setAttribute("assets", path + "/assets");
        LOG.info("Servlet context initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	LOG.info("Servlet context destroyed.");
    }

}
