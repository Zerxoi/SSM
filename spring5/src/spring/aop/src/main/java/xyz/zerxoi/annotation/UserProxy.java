package xyz.zerxoi.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class UserProxy {

    @Pointcut("execution(* xyz.zerxoi.annotation.User.hello(..))")
    public void pointcut() {

    }
    // 前置通知
    @Before("execution(* xyz.zerxoi.annotation.User.hello(..)) && args(xxx,..)")
    public void before(JoinPoint jp, String xxx) {
        System.out.println("dfasdfsdfwaefsabvkjaiefhwiuaekfhkajshfkjsfhi2qhr9f8q2h");
        System.out.println("name = " + xxx);
        System.out.println(jp.getArgs().length);
        System.out.println("Before advice");
    }

    // 后置通知
    @AfterReturning(value =  "pointcut()", returning = "retString")
    public void afterReturning(Integer retString) {
        System.out.println("=============="+retString);
        System.out.println("AfterReturning advice");
    }

    // 异常通知
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("AfterThrowing advice");
    }

    // 最终通知
    @After("pointcut()")
    public void after() {
        System.out.println("After advice");
    }

    // 环绕通知
    @Around("pointcut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Around advice - Before");
        proceedingJoinPoint.proceed();
        System.out.println("Around advice - After");
    }

}
