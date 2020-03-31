package com.learn.mq.server.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class AsyncTask {
    @Async
    public ListenableFuture<String> expensiveOperation(String message) {
        int millis = (int) (Math.random() * 1 * 1000 + 1000);
        try {
            System.out.println("模拟数据库操作...");
            Thread.sleep(millis);
            System.out.println("模拟结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = message + " executed by " + Thread.currentThread().getName();
        System.out.println(result);
        return new AsyncResult<>(result);
    }
}
