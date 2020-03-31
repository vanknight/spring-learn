package com.learn.mq.client.service;

import com.learn.mq.client.config.RabbitMQConfig;
import com.learn.mq.client.model.MessageModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class SenderService {
    private final AsyncRabbitTemplate asyncRabbitTemplate;
    private final AmqpTemplate amqpTemplate;

    public SenderService(AsyncRabbitTemplate asyncRabbitTemplate, AmqpTemplate amqpTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.amqpTemplate = amqpTemplate;
    }
    @Async
    public Future<Object> sendAsync(MessageModel messageModel) {
        String result = (String) amqpTemplate.convertSendAndReceive(RabbitMQConfig.QUEUE_ASYNC_RPC,messageModel);
        return new AsyncResult<>(result);
    }
    public Future<Object> sendWithFixedReplay(MessageModel messageModel){
        return asyncRabbitTemplate.convertSendAndReceive(RabbitMQConfig.QUEUE_ASYNC_RPC_WITH_FIXED_REPLY,messageModel);
    }
}
