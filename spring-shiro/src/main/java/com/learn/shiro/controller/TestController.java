package com.learn.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.shiro.api.model.BaseResponse;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {
    @GetMapping("/test01")
    @RequiresAuthentication
    public BaseResponse test01(@RequestBody(required = false) JSONObject object){
        if(object==null||object.isEmpty()){
            return new BaseResponse().error();
        }
        return new BaseResponse().ok(object);
    }
    @GetMapping("/test02")
    @RequiresRoles(value = {"user"})
    public BaseResponse test02(@RequestBody(required = false)JSONObject object){
        if(object==null||object.isEmpty()){
            return new BaseResponse().error();
        }
        return new BaseResponse().ok(object);
    }
    @GetMapping("/test03")
    @RequiresRoles(value = {"admin"})
    public BaseResponse test03(@RequestBody(required = false)JSONObject object){
        if(object==null||object.isEmpty()){
            return new BaseResponse().error();
        }
        return new BaseResponse().ok(object);
    }

    @GetMapping("/test04")
    @RequiresPermissions(value = "create")
    public BaseResponse test04(@RequestBody(required = false)JSONObject object){
        if(object==null||object.isEmpty()){
            return new BaseResponse().error();
        }
        return new BaseResponse().ok(object);
    }
    @GetMapping("/test05")
    @RequiresPermissions(value = "delete")
    public BaseResponse test05(@RequestBody(required = false)JSONObject object){
        if(object==null||object.isEmpty()){
            return new BaseResponse().error();
        }
        return new BaseResponse().ok(object);
    }
}
