package com.course.business.controller.admin;

import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * class:CourseCategoryController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@RestController
@RequestMapping("/admin/courseCategory")

public class CourseCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseCategoryController.class);
    public static final String BUSINESS_NAME = "课程分类";//

    @Resource
    private CourseCategoryService courseCategoryService;

    //访问courseCategory地址
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseCategoryDto courseCategoryDto) {

        // 保存校验
       // LOG.info("pageDto:{}",courseCategoryDto);
        // 后端的防止渗透校验 保存校验
       // ValidatorUtil.require(courseCategoryDto.getName(), "名称");
       // ValidatorUtil.require(courseCategoryDto.getCourseId(), "课程ID");
        // ValidatorUtil.length(courseCategoryDto.getCourseId(), "课程ID", 1, 8);

        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.save(courseCategoryDto);
        responseDto.setContent(courseCategoryDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.delete(id);
        return responseDto;
    }
}
