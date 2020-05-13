package com.course.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class:TestController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */
@RestController
public class TestController {

    //访问test地址
    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
