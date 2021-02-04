package xyz.zerxoi.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;

// @Component
// @Aspect
// @Order(2)
public class PersonProxy {
    // 前置通知
    @Before("xyz.zerxoi.annotation.UserProxy.pointcut()")
    public void before() {
        System.out.println("Person ====== Before advice");
    }

    // 后置通知
    @AfterReturning("xyz.zerxoi.annotation.UserProxy.pointcut()")
    public void afterReturning() {
        System.out.println("Person ====== AfterReturning advice");
    }

    // 异常通知
    @AfterThrowing("xyz.zerxoi.annotation.UserProxy.pointcut()")
    public void afterThrowing() {
        System.out.println("Person ====== AfterThrowing advice");
    }

    // 最终通知
    @After("xyz.zerxoi.annotation.UserProxy.pointcut()")
    public void after() {
        System.out.println("Person ====== After advice");
    }

    // 环绕通知
    @Around("xyz.zerxoi.annotation.UserProxy.pointcut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Person ====== Around advice - Before");
        proceedingJoinPoint.proceed();
        System.out.println("Person ====== Around advice - After");
    }
}
