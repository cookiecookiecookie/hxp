package com.coshine.batsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestContextListener;

import com.coshine.batsys.aop.MyBatisPageHelperInterceptor;
import com.coshine.batsys.aop.WebHandleInterceptorAdvisor;
import com.coshine.batsys.spring.ApplicationContextHolder;
import com.coshine.batsys.spring.mvc.HandlerExceptionViewResolver;
import com.coshine.batsys.web.WebsiteListener;

/**
 * Created by jia on 2015/7/5.
 */
@SpringBootApplication
public class BatsysApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BatsysApplication.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BatsysApplication.class);
	}

	@Bean
	public WebsiteListener websiteListener() {
		return new WebsiteListener();
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public HandlerExceptionViewResolver handlerExceptionViewResolver() {
		return new HandlerExceptionViewResolver();
	}

	@Bean
	public ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}

	@Bean
	public MyBatisPageHelperInterceptor myBatisPageHelperInterceptor() {
		return new MyBatisPageHelperInterceptor();
	}

	@Bean
	public WebHandleInterceptorAdvisor webHandleInterceptorAdvisor() {
		return new WebHandleInterceptorAdvisor();
	}

	@Bean
	public TaskExecutor TaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 线程池所使用的缓冲队列
		taskExecutor.setQueueCapacity(200);
		// 线程池维护线程的最少数量
		taskExecutor.setCorePoolSize(5);
		// 线程池维护线程的最大数量
		taskExecutor.setMaxPoolSize(1000);
		// 线程池维护线程所允许的空闲时间
		taskExecutor.setKeepAliveSeconds(30000);
		taskExecutor.initialize();
		return taskExecutor;
	}

}
