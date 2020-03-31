package com.learn.webflux.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisElementReader;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Deprecated
public class ReactiveRedisTemplateFactory<V extends Serializable> {
    private ReactiveRedisConnectionFactory factory;

    public ReactiveRedisTemplateFactory(ReactiveRedisConnectionFactory factory) {
        this.factory = factory;
    }

    public ReactiveRedisTemplate<String, V> template() {
        RedisSerializer<V> objSerializer = new FastJsonRedisSerializer<V>((Class<V>) Serializable.class);
        RedisSerializer<String> strSerializer = new StringRedisSerializer();
        ReactiveRedisTemplate<String, V> redisTemplate = new ReactiveRedisTemplate<String, V>(
                this.factory,
                RedisSerializationContext.<String, V>newSerializationContext()
                        .key(strSerializer)
                        .value(objSerializer)
                        .hashKey(strSerializer)
                        .hashValue(objSerializer)
                        .build()
        );
        return redisTemplate;
    }
}
