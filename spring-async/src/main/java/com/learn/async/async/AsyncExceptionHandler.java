package com.learn.async.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable e, Method m, Object... os) {
        if(e instanceof AsyncException){
            AsyncException exception = (AsyncException) e;
            System.out.println(e.getMessage());
        }
    }
}
