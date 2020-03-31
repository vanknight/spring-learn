package com.learn.webflux.controller;

import com.learn.webflux.model.MessageBase;
import com.learn.webflux.model.User;
import com.learn.webflux.repo.MessageBaseRepo;
import com.learn.webflux.service.RedisService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

@RestController
public class TestController {
    private final MessageBaseRepo messageBaseRepo;
    private final RedisService redisService;

    public TestController(MessageBaseRepo messageBaseRepo, RedisService redisService) {
        this.messageBaseRepo = messageBaseRepo;
        this.redisService = redisService;
    }

    @GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageBase> getAllMessageBase() {
        return messageBaseRepo.findAll();
    }

    @GetMapping("/messages/{id}")
    public Mono<MessageBase> getMessageBase(@PathVariable("id") String id) {
        return messageBaseRepo.findById(id);
    }

    @GetMapping(value = "/message/set/{key}")
    public Mono<Serializable> redisSet(@PathVariable("key") String key) {
        MessageBase messageBase = new MessageBase();
        messageBase.setId("1222312askldf");
        messageBase.setMsg(new Date().toString());
        return redisService.add(key, messageBase);
    }


    @GetMapping(value = "/user/set/{key}")
    public Mono<Serializable> redisSet2(@PathVariable("key") String key) {
        User user = new User();
        user.setName(new Date().toString());
        return redisService.add(key, user);
    }

    @GetMapping(value = "/get/{key}")
    public Mono<Serializable> redisGet(@PathVariable("key") String key) {
        return redisService.get(key);
    }


    @CrossOrigin("*")
    @GetMapping("/sse")
    public Flux<ServerSentEvent<String>> sse() {
        return Flux.interval(Duration.ofMillis(1000))
                .map(val -> {
                    return ServerSentEvent.<String>builder()
                            .data(val.toString())
                            .build();
                });
    }

}

