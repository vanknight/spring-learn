package com.learn.aop.controller;

import com.learn.aop.aspect.TestAnno;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test01")
    public void test01() {

    }

    @GetMapping("/test02")
    public String test02() {
        return "test02";
    }

    @GetMapping("/test03")
    public void test03() throws Exception {
        int i = 1 / 0;
    }

    @GetMapping("/test04")
    @TestAnno
    public void test04() {

    }
//    @GetMapping

}
