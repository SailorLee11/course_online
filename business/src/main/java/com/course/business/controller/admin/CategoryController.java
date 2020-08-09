package com.course.business.controller.admin;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CategoryService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * class:CategoryController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@RestController
@RequestMapping("/admin/category")

public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类";//

    @Resource
    private CategoryService categoryService;

    //访问category地址
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        categoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<CategoryDto> categoryDtoList =  categoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {

        // 保存校验
                ValidatorUtil.require(categoryDto.getParent(), "父id");
                ValidatorUtil.require(categoryDto.getName(), "名称");
                ValidatorUtil.length(categoryDto.getName(), "名称",1,50);
       // LOG.info("pageDto:{}",categoryDto);
        // 后端的防止渗透校验 保存校验
       // ValidatorUtil.require(categoryDto.getName(), "名称");
       // ValidatorUtil.require(categoryDto.getCourseId(), "课程ID");
        // ValidatorUtil.length(categoryDto.getCourseId(), "课程ID", 1, 8);

        ResponseDto responseDto = new ResponseDto();
        categoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        categoryService.delete(id);
        return responseDto;
    }
}
