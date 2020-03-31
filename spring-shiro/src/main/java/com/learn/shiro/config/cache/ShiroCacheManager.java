package com.learn.shiro.config.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

public class ShiroCacheManager implements CacheManager {
    @Resource
    private ShiroRedisCache shiroRedisCache;
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        if(shiroRedisCache.test()){
            return shiroRedisCache;
        }
        return new MCache<>();
    }
}
