package com.learn.async.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTask {
    @Async
    public void dealNoReturnTask(){
        System.out.println("noReturn start");
        try {
            Thread.sleep(5000);
            System.out.println("noReturn sleep end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("noReturn end");
    }
    @Async
    public Future<String> dealHaveReturnTask(int i){
        Future<String> future;
        System.out.println("return start");
        try {
            Thread.sleep(5000);
            System.out.println("return sleep end");
            future = new AsyncResult<>("success: "+i);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("return error end");
            future = new AsyncResult<>("error: "+i);
        }
        System.out.println("return end");
        return future;
    }
}
