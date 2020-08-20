package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
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
 * class:TeacherController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();

        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
//        将查出来的teacherlist传到分页里面去
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
//      gettotal获取总行数
        pageDto.setTotal(pageInfo.getTotal());
        // for (int i = 0,l = teacherList.size();i<l;i++){
           // Teacher teacher =  teacherList.get(i);
           // TeacherDto teacherDto = new TeacherDto();
            //BeanUtils.copyProperties(teacher,teacherDto);
          //  teacherDtoList.add(teacherDto);
        //}
        pageDto.setList(teacherDtoList);
    }

    public void save(TeacherDto teacherDto){
        Teacher teacher = CopyUtil.copy(teacherDto,Teacher.class);
        if (StringUtils.isEmpty(teacher.getId())) {
            this.insert(teacher);
        } else {
            this.update(teacher);
        }
    }

    private void insert(Teacher teacher){

        //生成一个新的id
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    private void update(Teacher teacher){
        teacherMapper.updateByPrimaryKey(teacher);
    }

    public void delete(String id){
        teacherMapper.deleteByPrimaryKey(id);
    }
}