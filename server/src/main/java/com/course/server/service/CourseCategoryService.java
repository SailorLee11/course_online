package com.course.server.service;

import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * class:CourseCategoryController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@Service
public class CourseCategoryService {

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();

        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
//        将查出来的courseCategorylist传到分页里面去
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
//      gettotal获取总行数
        pageDto.setTotal(pageInfo.getTotal());
        // for (int i = 0,l = courseCategoryList.size();i<l;i++){
           // CourseCategory courseCategory =  courseCategoryList.get(i);
           // CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            //BeanUtils.copyProperties(courseCategory,courseCategoryDto);
          //  courseCategoryDtoList.add(courseCategoryDto);
        //}
        pageDto.setList(courseCategoryDtoList);
    }

    public void save(CourseCategoryDto courseCategoryDto){
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto,CourseCategory.class);
        if (StringUtils.isEmpty(courseCategory.getId())) {
            this.insert(courseCategory);
        } else {
            this.update(courseCategory);
        }
    }

    private void insert(CourseCategory courseCategory){

        //生成一个新的id
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    private void update(CourseCategory courseCategory){
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    public void delete(String id){
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * @param dtoList
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
//        清空
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
//保存批量
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }
    }
}