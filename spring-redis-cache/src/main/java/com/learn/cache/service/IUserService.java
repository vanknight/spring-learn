package com.learn.cache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.cache.dao.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    User getByIdCache(int id);
    void deleteByIdCache(int id);
    List<User> listCache();
    User updateByIdCache(User user);

}
