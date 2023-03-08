package com.example.letscompete.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(Log)")
    public void logAnnotation(){

    }

    @After("logAnnotation()")
    public void logMethodCallAdvice(JoinPoint joinPoint){
        log.info("aspect log after " + joinPoint.getSignature());
    }


    @Pointcut("within(com.example.letscompete.*)")
    public void logPackage(){

    }

    @Before("logPackage()")
    public void logPackageAdvice(JoinPoint joinPoint){
        log.info("aspect log before " + joinPoint.getSignature());
    }


    @Pointcut("execution(public com.example.letscompete.model.Game *.save(..))")
    public void logMethod(){

    }

    @After("logMethod()")
    public void logSetterAdvice(JoinPoint joinPoint){
        log.info("aspect log after " + joinPoint.getSignature());
        log.info("aspect log param " + joinPoint.getArgs()[0]);

    }
}
