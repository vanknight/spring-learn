package com.learn.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.cache.dao.entity.User;
import com.learn.cache.dao.repository.UserMapper;
import com.learn.cache.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Test01 {
    @Autowired
    private IUserService userService;

    @Test
    void test01() {
        BaseMapper<User> userMapper = userService.getBaseMapper();
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda();
        wrapper.eq(User::getId, 20);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void test02() {
        int id = 3;
        long start = System.currentTimeMillis();
        userService.deleteByIdCache(id);
        List<User> list = userService.listCache();
        User user1 = userService.getByIdCache(id);
        System.out.println(user1);
        System.out.println(list);
        user1.setUpdateTime("2018-01-01 02:02:00");
        userService.updateByIdCache(user1);
        user1 = userService.getByIdCache(3);
        System.out.println(user1);
        list = userService.listCache();
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start) + "ms"); System.out.println();

    }


}
