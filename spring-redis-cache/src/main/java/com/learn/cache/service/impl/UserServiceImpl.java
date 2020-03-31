package com.learn.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.cache.dao.entity.User;
import com.learn.cache.dao.repository.UserMapper;
import com.learn.cache.service.IUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Caching(cacheable = {
            @Cacheable(value = "userCache", key = "#id", unless = "#result==null")
    })
    public User getByIdCache(int id) {
        System.out.println("未缓存，从数据库获取用户中...");
        return this.baseMapper.selectById(id);
    }

    @Cacheable(value = "allUserCache", unless = "#result.size()==0")
    public List<User> listCache() {
        System.out.println("未缓存，从数据库获取所有用户中...");
        return this.baseMapper.selectList(null);

    }

    @Caching(
            put = {@CachePut(value = "userCache", key = "#user.id")},
            evict = {@CacheEvict(value = "allUserCache", allEntries = true)}
    )
    public User updateByIdCache(User user) {
        System.out.println("更新数据中...");
        this.baseMapper.updateById(user);
        return user;
    }

    @Caching(evict = {
            @CacheEvict(value = "userCache", key = "#id"),
            @CacheEvict(value = "allUserCache",allEntries = true)
    })
    public void deleteByIdCache(int id) {
        System.out.println("删除缓存");
//        this.baseMapper.deleteById(id);
    }

}
