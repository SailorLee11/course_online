package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * class:ChapterController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@RestController
@RequestMapping("/admin/chapter")

public class ChapterController {

    @Resource
    private ChapterService chapterService;

    //访问chapter地址
    @RequestMapping("/list")
    public List<ChapterDto> list(){
        return chapterService.list();
    }
}
