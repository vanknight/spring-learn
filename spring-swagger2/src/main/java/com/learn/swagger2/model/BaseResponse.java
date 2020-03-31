package com.learn.swagger2.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BaseResponse")
public class BaseResponse {
    @ApiModelProperty(value = "错误代码")
    private String code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private JSONObject data;

    public BaseResponse() {
        set("000", "success", null);
    }

    public void set(String code, String msg, JSONObject data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    public BaseResponse ok(Object data) {
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(data));
        set("000", "success", object);
        return this;
    }

    public BaseResponse ok(String msg, Object data) {
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(data));
        set("000", msg, object);
        return this;
    }

    public BaseResponse ok(String msg) {
        set("000", msg, null);
        return this;
    }

    public BaseResponse error() {
        set("000", "fail", null);
        return this;
    }

    public BaseResponse error(String msg) {
        set("010", msg, null);
        return this;
    }
}
