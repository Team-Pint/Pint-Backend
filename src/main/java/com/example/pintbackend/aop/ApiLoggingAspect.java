/**
 * File: null.java
 * Path: com.example.pintbackend.aop
 * <p>
 * Outline:
 * using AOP to measure how long it takes to execute an api request
 * <p>
 * Author: jskt
 */

package com.example.pintbackend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ApiLoggingAspect {

    @Around("execution(* com.example.pintbackend.controller..*(..)) || execution(* com.example.pintbackend.service..*(..))")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();

        try {
            Object result = joinPoint.proceed();
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            log.info("{} Execution Time - {} : {}ms",
                    getExecutionLayer(joinPoint),
                    joinPoint.getSignature().toShortString(),
                    elapsedMs);
            return result;
        } catch (Throwable ex) {
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            log.warn("{} Execution Time (with exception) - {} : {}ms",
                    getExecutionLayer(joinPoint),
                    joinPoint.getSignature().toShortString(),
                    elapsedMs);
            throw ex;
        }
    }

    private String getExecutionLayer(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (className.contains(".service.")) {
            return "[Service]";
        }
        if (className.contains(".controller.")) {
            return "[Controller]";
        }
        return "[Target]";
    }
}
