package com.coshine.batsys.aop;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

/**
 * Created by jia on 2015/7/26.
 */
public interface WebHandleInterceptor extends Ordered {

    void preHandle(MethodSignature methodSignature) throws Exception;
}
