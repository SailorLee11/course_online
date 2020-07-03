package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * class:SectionController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
//        将查出来的sectionlist传到分页里面去
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        List<SectionDto>sectionDtoList = new ArrayList<SectionDto>();
//      gettotal获取总行数
        pageDto.setTotal(pageInfo.getTotal());
        for (int i = 0,l = sectionList.size();i<l;i++){
            Section section =  sectionList.get(i);
            SectionDto sectionDto = new SectionDto();
            BeanUtils.copyProperties(section,sectionDto);
            sectionDtoList.add(sectionDto);
        }
        pageDto.setList(sectionDtoList);
    }

    public void save(SectionDto sectionDto){
        Section section = CopyUtil.copy(sectionDto,Section.class);
        if (StringUtils.isEmpty(section.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
    }

    private void insert(Section section){
        //生成一个新的id
        section.setId(UuidUtil.getShortUuid());
//        Section section = new Section();
//        BeanUtils.copyProperties(section,section);
        sectionMapper.insert(section);
    }

    private void update(Section section){
        sectionMapper.updateByPrimaryKey(section);
    }

    public void delete(String id){
        sectionMapper.deleteByPrimaryKey(id);
    }
}