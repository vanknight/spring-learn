package com.learn.mq.client;

import com.alibaba.fastjson.JSONObject;
import com.learn.mq.client.model.MessageModel;
import com.learn.mq.client.service.SenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
class RabbitmqRpcClientApplicationTests {
    @Autowired
    private SenderService senderService;

    @Test
    void test01() {
        List<Future<Object>> futures = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-HH-MM hh:mm:ss");
        for (int i = 0; i < 4; i++) {
            MessageModel messageModel = new MessageModel();
            messageModel.setMessage(format.format(new Date()));
            System.out.println("发送内容: " + JSONObject.toJSONString(messageModel));
            Future<Object> future = senderService.sendAsync(messageModel);
            futures.add(future);
        }
        System.out.println("发送完毕");
        futures.forEach(future -> {
            try {
                Object result = future.get();

                String resultStr = "";
                byte[] resultB;
                if (result instanceof byte[]) {
                    resultB = (byte[]) result;
                    resultStr = new String(resultB);
                } else if (result instanceof String) {
                    resultStr = (String) result;
                }else{
                    System.out.println("或许返回类型有问题");
                }
                System.out.println("返回: "+resultStr);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("其他任务");
    }

}
