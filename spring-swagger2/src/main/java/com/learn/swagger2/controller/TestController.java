package com.learn.swagger2.controller;

import com.learn.swagger2.model.BaseResponse;
import com.learn.swagger2.model.ParamModel;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "测试接口类",tags = "测试")
@ApiResponses(value = {
        @ApiResponse(code= 200,message = "请求完成")
})
@RequestMapping("/test")
public class TestController {
    @ApiOperation(value = "测试一接口",notes = "测试01")
    @ApiImplicitParam(name = "paramModel",value = "参数",dataType = "ParamModel")
    @GetMapping("/test01")
    public BaseResponse test01(@RequestBody ParamModel paramModel){
        return new BaseResponse().ok(paramModel);
    }
}
