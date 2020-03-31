package com.learn.webflux.repo;

import com.learn.webflux.model.MessageBase;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageBaseRepo extends ReactiveMongoRepository<MessageBase,String> {
}
