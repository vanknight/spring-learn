package com.learn.shiro.config.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Component
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    private Class<? extends Object> vClz = SimpleAuthorizationInfo.class;
    /**
     * <string,authorizationInfo>
     */
    private final RedisTemplate<String, Serializable> redisTemplate;

    public ShiroRedisCache(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String keyToString(K k) {
        if (k instanceof String) {
            return (String) k;
        }
        if (k instanceof JSON) {
            return ((JSON) k).toJSONString();
        }
        return k.toString();
    }

    private String valueToString(V v) {
        if (v instanceof String) {
            return String.valueOf(v);
        }
        return JSONObject.toJSONString(v);
    }

    private Object parseValue(Serializable ser) {
        if (ser instanceof String) {
            Object o = JSONObject.parseObject(String.valueOf(ser), vClz);
            String s = JSONObject.toJSONString(o);
            boolean equals = String.valueOf(ser).equals(s);
            if (equals) {
                return o;
            }else{
                return JSONObject.parseObject(String.valueOf(ser));
            }
        }
        return ser;
    }

    @Override
    public V get(K k) throws CacheException {
        Serializable serializable = redisTemplate.opsForValue().get(keyToString(k));
        return (V) parseValue(serializable);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String value = valueToString(v);
        String key = keyToString(k);
        redisTemplate.opsForValue().set(key, value);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
    public boolean test(){
        if(redisTemplate!=null){
            String key = "redis_test";
            key += String.valueOf(new Date().getTime());
            try {
                redisTemplate.opsForValue().set(key, "1");
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
