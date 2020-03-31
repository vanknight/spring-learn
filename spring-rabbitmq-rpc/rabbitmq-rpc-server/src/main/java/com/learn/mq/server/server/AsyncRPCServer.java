package com.learn.mq.server.server;

import com.alibaba.fastjson.JSONObject;
import com.learn.mq.server.config.RabbitMQConfig;
import com.learn.mq.server.model.MessageModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;

@Component
public class AsyncRPCServer {
    private final AmqpTemplate amqpTemplate;
    private final AsyncTask asyncTask;

    public AsyncRPCServer(AmqpTemplate amqpTemplate, AsyncTask asyncTask) {
        this.amqpTemplate = amqpTemplate;
        this.asyncTask = asyncTask;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ASYNC_RPC)
    public void processAsyncRpc(
            @Payload JSONObject object,
            @Header(AmqpHeaders.REPLY_TO) String replyTo,
            @Header(AmqpHeaders.CORRELATION_ID) String correlationId
    ) {
        System.out.println("obj: " + object);
        MessageModel messageModel = object.toJavaObject(MessageModel.class);
        System.out.println("model: " + messageModel);
        ListenableFuture<String> asyncResult = asyncTask.expensiveOperation(messageModel.getMessage());
        asyncResult.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("接收到QUEUE_ASYNC_RPC的ERROR");
            }

            @Override
            public void onSuccess(String result) {
                MessageModel m1 = new MessageModel();
                m1.setMessage(result);
                amqpTemplate.convertAndSend(replyTo, result, m -> {
                    m.getMessageProperties().setCorrelationId(correlationId);
                    return m;
                });
            }
        });

    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ASYNC_RPC_WITH_FIXED_REPLY)
    public void processAsyncRpcFixed(
            @Payload JSONObject object,
            @Header(AmqpHeaders.REPLY_TO) String replyTo,
            @Header(AmqpHeaders.CORRELATION_ID) String correlationId
    ) {
        System.out.println("obj: " + object);
        MessageModel messageModel = object.toJavaObject(MessageModel.class);
        System.out.println("model: " + messageModel);
        ListenableFuture<String> asyncResult = asyncTask.expensiveOperation(messageModel.getMessage());
        asyncResult.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("收到QUEUE_ASYNC_RPC_WITH_FIXED_REPLY的ERROR");
            }

            @Override
            public void onSuccess(String result) {
                MessageModel m1 = new MessageModel();
                m1.setMessage(result);
                amqpTemplate.convertAndSend(replyTo, result, m -> {
                    m.getMessageProperties().setCorrelationId(correlationId);
                    return m;
                });

            }
        });
    }

}
