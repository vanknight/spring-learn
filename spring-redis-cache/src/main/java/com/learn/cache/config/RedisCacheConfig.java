package com.learn.cache.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {
    @Autowired
    private Environment env;

    @Bean
    public RedisSerializer<Object> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(fastJsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
//        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        return redisTemplate;
//    }

//    @Bean
//    public RedisCacheConfiguration cacheConfiguration(){
//        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(600))
//                .disableCachingNullValues();
//        return cacheConfig;
//    }
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager rcm = RedisCacheManager.builder(redisFactory)
//                .cacheDefaults(cacheConfiguration())
//                .transactionAware()
//                .build();
//        return rcm;
//    }
//    @Bean
//    public KeyGenerator keyGenerator(){
//        return (o,method,params) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(o.getClass().getName()).append(".");
//            for (Object obj:params){
//                sb.append(obj.toString()).append("_");
//            }
//            sb.append("key");
//            System.out.println(sb.toString());
//            return sb.toString();
//        };
//    }

}
