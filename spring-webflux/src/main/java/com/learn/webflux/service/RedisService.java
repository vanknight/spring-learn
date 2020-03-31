package com.learn.webflux.service;

import com.learn.webflux.config.ReactiveRedisTemplateFactory;
import com.learn.webflux.model.MessageBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.Serializable;

@Service
public class RedisService {
    private final ReactiveRedisTemplate<Serializable, Serializable> template;

    public RedisService(@Qualifier("customTemplate") ReactiveRedisTemplate<Serializable, Serializable> template) {
        this.template = template;
    }

    public Mono<Serializable> get(Serializable key) {
        ReactiveValueOperations<Serializable, Serializable> operations = template.opsForValue();
        return operations.get(key);
    }

    public Mono<Serializable> add(Serializable key, Serializable obj) {
        ReactiveValueOperations<Serializable, Serializable> operations = template.opsForValue();
        return operations.getAndSet(key, obj);
    }

}
