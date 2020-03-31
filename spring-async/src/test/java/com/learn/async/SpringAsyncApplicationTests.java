package com.learn.async;

import com.learn.async.async.AsyncTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
class SpringAsyncApplicationTests {
    @Autowired
    private AsyncTask asyncTask;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        asyncTask.dealNoReturnTask();
        System.out.println("dealNoReturnTask over");
        Future<String> future = asyncTask.dealHaveReturnTask(2);
        System.out.println("dealHaveReturnTask over");
        String s = future.get();
        System.out.println("return: " + s);
        System.out.println("all over");
    }

}
