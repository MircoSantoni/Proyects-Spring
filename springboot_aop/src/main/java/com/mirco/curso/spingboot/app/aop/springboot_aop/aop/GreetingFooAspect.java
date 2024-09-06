package com.mirco.curso.spingboot.app.aop.springboot_aop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Aspect
@Component
public class GreetingFooAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());



        @Before("GreetingServicePointcuts.greetingFooAspectPointCut()")
    public void loggerBefore(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes: " + method + " invocado con los argumentos: " + arg);
    }


}
