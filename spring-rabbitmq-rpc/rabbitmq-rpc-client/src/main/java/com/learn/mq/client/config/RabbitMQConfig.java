package com.learn.mq.client.config;

import com.learn.mq.client.config.covert.ClientMessageConverter;
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

    /**
     * 同步RPC队列
     */
    public static final String QUEUE_SYNC_RPC = "rpc.sync";

    /**
     * 异步RPC队列，使用临时回复队列，或者使用“Direct reply-to”特性
     */
    public static final String QUEUE_ASYNC_RPC = "rpc.async";

    /**
     * 异步RPC队列，每个客户端使用不同的固定回复队列，需要额外提供correlationId以关联请求和响应
     */
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
    public MessageConverter clientMessageConverter() {
        return new ClientMessageConverter();
    }

    @Bean
    @Primary
    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(repliesQueue().getName());
        return container;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(clientMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, SimpleMessageListenerContainer container) {
        return new AsyncRabbitTemplate(template, container);
    }
}
