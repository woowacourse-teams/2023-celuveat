package com.celuveat.common.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAop {

    private final Logger logger;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerAnnotatedClass() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceAnnotatedClass() {
    }

    @Pointcut("execution(* com.celuveat..*Repository.*(..))")
    public void repositoryClass() {
    }

    @Around("restControllerAnnotatedClass() || serviceAnnotatedClass() || repositoryClass()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = getMethodName(joinPoint);
        String className = getClassSimpleName(joinPoint);
        logger.methodCall(className, methodName);
        try {
            Object result = joinPoint.proceed();
            logger.methodReturn(className, methodName);
            return result;
        } catch (Throwable e) {
            logger.throwException(className, methodName, e);
            throw e;
        }
    }

    private String getClassSimpleName(ProceedingJoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        String className = clazz.getSimpleName();
        if (className.contains("Proxy")) {
            className = clazz.getInterfaces()[0].getSimpleName();
        }
        return className;
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
