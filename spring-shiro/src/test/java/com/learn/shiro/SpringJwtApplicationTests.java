package com.learn.shiro;

import com.alibaba.fastjson.JSON;
import com.learn.shiro.model.AuthManagerInfo;
import com.learn.shiro.service.UserManagerService;
import com.learn.shiro.utils.ShiroKit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringJwtApplicationTests {
    @Resource
    private UserManagerService managerService;

    @Test
    void test01() {
        AuthManagerInfo managerInfo = managerService.getManagerInfo("admin");
        String json = JSON.toJSONString(managerInfo);
        System.out.println(json);
    }
    @Test
    void test02(){
        AuthManagerInfo managerInfo = managerService.getManagerInfo("admin");
        String json = JSON.toJSONString(managerInfo);
        System.out.println(json);

    }
    @Test
    void test03(){
        String username = "admin";
        String salt = "asdf";
        String pwd = "123456";
        //db8b543ffd86eb127bd82626f6dd815d
        String encodePwd = ShiroKit.md5(pwd,username+salt);
        System.out.println(encodePwd);
    }

}
