package com.learn.webflux.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisDefaultConfig {
    @Bean
    public RedisSerializer<Serializable> redisSerializer() {
        return new FastJsonRedisSerializer<>(Serializable.class);
    }

    @Bean
    @Qualifier("customTemplate")
    public ReactiveRedisTemplate<Serializable,Serializable> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.fromSerializer(redisSerializer()));
    }
}
