package com.learn.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.AbstractListenerReadPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
@Order(1)
public class TestAspect {
    /**
     * 所有声明为公共的，在com.learn.aop.controller包下的所有类的所有方法
     */
    @Pointcut("execution(public * com.learn.aop.controller.*.*(..))")
    public void webLog() {
    }


    @Before("webLog()")
    public void deBefore(JoinPoint jp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //打印
        System.out.println("-----Before Start:");
        System.out.println("url: " + request.getRequestURL().toString());
        System.out.println("method: " + request.getMethod());
        System.out.println("classMethod: " + jp.getSignature().getDeclaringTypeName() + "-" + jp.getSignature().getName());
        System.out.println("args: " + Arrays.toString(jp.getArgs()));
        System.out.println("-----Before End; ");

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        System.out.println("-----AfterReturning Start:");
        System.out.println("method-return: " + ret);
        System.out.println("-----AfterReturning End;");
        System.out.println("-------------------------------------------------");
    }

    @After("webLog()")
    public void doAfter(JoinPoint jp) {
        System.out.println("-----After Start:");
        System.out.println("-----After End;");

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("-----Around Start:");
        System.out.println("-----Around MethodStart");
        Object ret = pjp.proceed();
        System.out.println("-----Around OverMethod");
        System.out.println("return: " + ret);
        System.out.println("-----Around End;");
        return ret;
    }

    @AfterThrowing(pointcut = "webLog()", throwing = "e")
    public void error(JoinPoint jp, Throwable e) {
        System.out.println("-----AfterThrowing Start:");
        System.out.println("error: \n\tclassMethod: " + jp.getSignature().getDeclaringTypeName() + "-" + jp.getSignature().getName());
        System.out.println("\te: " + e.getMessage());
        System.out.println("-----AfterThrowing End;");
    }

}
