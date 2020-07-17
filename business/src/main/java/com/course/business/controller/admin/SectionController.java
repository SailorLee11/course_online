package com.course.business.controller.admin;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.service.SectionService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * class:SectionController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@RestController
@RequestMapping("/admin/section")

public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME = "小节";//

    @Resource
    private SectionService sectionService;

    //访问section地址
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        sectionService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto) {

        // 保存校验
                ValidatorUtil.require(sectionDto.getTitle(), "标题");
                ValidatorUtil.length(sectionDto.getTitle(), "标题",1,50);
                ValidatorUtil.length(sectionDto.getVideo(), "视频",1,200);
       // LOG.info("pageDto:{}",sectionDto);
        // 后端的防止渗透校验 保存校验
       // ValidatorUtil.require(sectionDto.getName(), "名称");
       // ValidatorUtil.require(sectionDto.getCourseId(), "课程ID");
        // ValidatorUtil.length(sectionDto.getCourseId(), "课程ID", 1, 8);

        ResponseDto responseDto = new ResponseDto();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        sectionService.delete(id);
        return responseDto;
    }
}
