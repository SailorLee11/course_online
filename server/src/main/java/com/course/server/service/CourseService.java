package com.course.server.service;

import com.course.server.domain.Course;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * class:CourseController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@Service
public class CourseService {

     private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);
     
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper mycourseMapper;

    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample courseExample = new CourseExample();
                courseExample.setOrderByClause("sort asc");

        List<Course> courseList = courseMapper.selectByExample(courseExample);
//        将查出来的courselist传到分页里面去
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);
//      gettotal获取总行数
        pageDto.setTotal(pageInfo.getTotal());
        // for (int i = 0,l = courseList.size();i<l;i++){
           // Course course =  courseList.get(i);
           // CourseDto courseDto = new CourseDto();
            //BeanUtils.copyProperties(course,courseDto);
          //  courseDtoList.add(courseDto);
        //}
        pageDto.setList(courseDtoList);
    }

    public void save(CourseDto courseDto){
        Course course = CopyUtil.copy(courseDto,Course.class);
        if (StringUtils.isEmpty(course.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }
    }

    private void insert(Course course){
        Date now = new Date();
                course.setCreatedAt(now);
                course.setUpdatedAt(now);
        //生成一个新的id
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    private void update(Course course){
                course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    public void delete(String id){
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新课程时长
     * @param courseId
     * @return
     */
    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        mycourseMapper.updateTime(courseId);
    }
}