package com.mirco.curso.spingboot.app.aop.springboot_aop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Aspect
@Component
public class GreetingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("GreetingServicePointcuts.greetingLoggerPointCut()")
    public void loggerBefore(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes: " + method + " Con los argumentos: " + arg);
    }
    
    @After("GreetingServicePointcuts.greetingLoggerPointCut()")
    public void loggerAfter(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues: " + method + " Con los argumentos: " + arg);
    }

    @AfterReturning("GreetingServicePointcuts.greetingLoggerPointCut()")
    public void loggerAfterReturning(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues de retornar: " + method + " Con los argumentos: " + arg);
    }

    @AfterThrowing("GreetingServicePointcuts.greetingLoggerPointCut()")
    public void loggerAfterThrowing(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues de lanzar excepcion: " + method + " Con los argumentos: " + arg);
    }

    @Around("GreetingServicePointcuts.greetingLoggerPointCut()")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String method = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        Object result = null;
        try {
            logger.info("el metodo: " + method + "( con los parametros: )" + arg);
            result = joinPoint.proceed();
            logger.info("el metodo: " + method + "( con el resultado : )" + result);
            return result;
            
        } catch (Throwable e) {
            logger.error("Error en la llamada del metodo: " + method + "()");
            throw e;
        }

    }
}
