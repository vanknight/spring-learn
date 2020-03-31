package com.learn.shiro.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.learn.shiro.api.model.BaseResponse;
import com.learn.shiro.model.AuthManagerInfo;
import com.learn.shiro.model.LoginParam;
import com.learn.shiro.service.UserManagerService;
import com.learn.shiro.utils.JWTUtil;
import com.learn.shiro.utils.ShiroKit;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class LoginController {
    @Resource
    private UserManagerService userManagerService;
    @PostMapping(value = "/login",produces = {"application/json"})
    public BaseResponse login(@RequestBody LoginParam loginParam){
        JSONObject result = new JSONObject();
        AuthManagerInfo info = userManagerService.getManagerInfo(loginParam.getUsername());
        String salt = info.getUser().getSalt();
        String encodePwd = ShiroKit.md5(loginParam.getPassword(),loginParam.getUsername()+salt);
        if(info.getUser().getPassword().equals(encodePwd)){
            result.put("jwt", JWTUtil.sign(loginParam.getUsername(),encodePwd));
            return new BaseResponse().ok(result);
        }

        throw new UnauthenticatedException();
    }
}
