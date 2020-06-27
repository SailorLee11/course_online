package com.course.business.controller;

import com.course.server.domain.Test;
import com.course.server.mapper.TestMapper;
import com.course.server.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * class:TestController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@RestController
public class TestController {

    @Resource
    private TestService testService;

    //访问test地址
    @RequestMapping("/test")
    public List<Test> test(){
        return null;
    }
}
