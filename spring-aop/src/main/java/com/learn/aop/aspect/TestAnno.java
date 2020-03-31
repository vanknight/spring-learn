package com.learn.aop.aspect;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnno {
    String value() default "this is empty";
}
