package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.enums.SectionChargeEnum;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
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

    public void list(SectionPageDto sectionPageDto){
        PageHelper.startPage(sectionPageDto.getPage(),sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
//        将查出来的sectionlist传到分页里面去
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
//      gettotal获取总行数
        sectionPageDto.setTotal(pageInfo.getTotal());
        // for (int i = 0,l = sectionList.size();i<l;i++){
           // Section section =  sectionList.get(i);
           // SectionDto sectionDto = new SectionDto();
            //BeanUtils.copyProperties(section,sectionDto);
          //  sectionDtoList.add(sectionDto);
        //}
        sectionPageDto.setList(sectionDtoList);
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
        Date now = new Date();
                section.setCreatedAt(now);
                section.setUpdatedAt(now);
        //生成一个新的id
        section.setId(UuidUtil.getShortUuid());
        section.setCharge(SectionChargeEnum.CHARGE.getCode());
        sectionMapper.insert(section);
    }

    private void update(Section section){
                section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    public void delete(String id){
        sectionMapper.deleteByPrimaryKey(id);
    }
}