package com.learn.shiro.config.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MCache<K,V> implements Cache<K,V> {
    private ConcurrentHashMap<K,V> map = new ConcurrentHashMap<>();
    @Override
    public V get(K k) throws CacheException {
        return map.get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        return map.put(k,v);
    }

    @Override
    public V remove(K k) throws CacheException {
        return map.remove(k);
    }

    @Override
    public void clear() throws CacheException {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Set<K> keys() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }
}
