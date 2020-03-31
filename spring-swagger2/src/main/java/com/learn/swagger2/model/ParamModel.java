package com.learn.swagger2.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "一般请求参数")
public class ParamModel {
    @ApiModelProperty(name = "ipAddr", dataType = "String", value = "IP地址")
    private String ipAddr;
    @ApiModelProperty(name = "param", dataType = "JSONObject", value = "Get请求参数")
    private JSONObject param;
    @ApiModelProperty(name = "body", dataType = "JSONObject", value = "Post请求参数")
    private JSONObject body;
}
