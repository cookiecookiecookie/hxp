package com.coshine.batsys.aop;

import org.apache.ibatis.session.RowBounds;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.PageHelper;

/**
 * Created by json on 2015/7/11.
 */
@Aspect
public class MyBatisPageHelperInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MyBatisPageHelperInterceptor.class);

    @Before("execution(* com.hp.crm.mapper..*Mapper.*(..))")
    public void pageHandle(JoinPoint joinPoint) {
    	Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg != null && arg instanceof RowBounds) {
				LOG.info("joinPoint: {}", joinPoint);
				RowBounds rb = (RowBounds)arg;
				PageHelper.startPage(rb.getOffset(), rb.getLimit());
				break;
			}
		}
    }
}
