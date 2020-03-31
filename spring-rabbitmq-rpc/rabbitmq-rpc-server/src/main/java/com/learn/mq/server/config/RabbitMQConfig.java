package com.learn.mq.server.config;

import com.learn.mq.server.config.covert.ServerMessageConverter;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_SYNC_RPC = "rpc.sync";
    public static final String QUEUE_ASYNC_RPC = "rpc.async";
    public static final String QUEUE_ASYNC_RPC_WITH_FIXED_REPLY = "rpc.with.fixed.reply";

    @Bean
    public Queue syncRPCQueue() {
        return new Queue(QUEUE_SYNC_RPC);
    }

    @Bean
    public Queue asyncRPCQueue() {
        return new Queue(QUEUE_ASYNC_RPC);
    }

    @Bean
    public Queue fixedReplyRPCQueue() {
        return new Queue(QUEUE_ASYNC_RPC_WITH_FIXED_REPLY);
    }

    @Bean
    public Queue repliesQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public MessageConverter serverMessageConverter() {
        return new ServerMessageConverter();
    }

    @Bean
    @Primary
    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(repliesQueue().getName());
        return container;
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, SimpleMessageListenerContainer container) {
        return new AsyncRabbitTemplate(template, container);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(serverMessageConverter());
        return rabbitTemplate;
    }

}
