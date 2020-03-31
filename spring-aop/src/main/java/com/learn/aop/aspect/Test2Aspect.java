package com.learn.aop.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class Test2Aspect {

    @Pointcut("@annotation(com.learn.aop.aspect.TestAnno)")
    public void annoLog() {
    }

    @Before("@annotation(testAnno)")
    public void doBefore2(TestAnno testAnno) {
        System.out.println("*----Annotation Before Start: ");
        System.out.println("Annotation: " + testAnno.value());
        System.out.println("-----Annotation Before End: ");

    }
}
