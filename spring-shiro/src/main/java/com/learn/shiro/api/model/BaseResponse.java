package com.learn.shiro.api.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BaseResponse {
    private String msg;
    private String code;
    private Object data;
    public BaseResponse(){
        this("000","success",new JSONObject());
    }
    public BaseResponse(String code,String msg,JSONObject data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    private void set(String code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public BaseResponse ok(){
        set("000","success",new JSONObject());
        return this;
    }
    public BaseResponse error(){
        set("001","error",new JSONObject());
        return this;
    }
    public BaseResponse ok(Map<String,Object> map){
        set("000","success",new JSONObject(map));
        return this;
    }
    public BaseResponse ok(List<Object> list){
        set("000","success",new JSONArray(list));
        return this;
    }
    public BaseResponse ok(JSONObject object){
        set("000","success",object);
        return this;
    }

}
