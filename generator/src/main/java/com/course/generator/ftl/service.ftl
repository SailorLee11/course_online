package com.course.server.service;

import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.${Domain}Mapper;
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

<#list typeSet as type>
    <#if type == 'Date'>
        import java.util.Date;
    </#if>
</#list>

/**
 * class:${Domain}Controller
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@Service
public class ${Domain}Service {

    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump == 'sort'>
                ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>

        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
//        将查出来的${domain}list传到分页里面去
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Dto.class);
//      gettotal获取总行数
        pageDto.setTotal(pageInfo.getTotal());
        // for (int i = 0,l = ${domain}List.size();i<l;i++){
           // ${Domain} ${domain} =  ${domain}List.get(i);
           // ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            //BeanUtils.copyProperties(${domain},${domain}Dto);
          //  ${domain}DtoList.add(${domain}Dto);
        //}
        pageDto.setList(${domain}DtoList);
    }

    public void save(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto,${Domain}.class);
        if (StringUtils.isEmpty(${domain}.getId())) {
            this.insert(${domain});
        } else {
            this.update(${domain});
        }
    }

    private void insert(${Domain} ${domain}){

    <#list typeSet as type>
        <#if type == 'Date'>
            Date now = new Date();
        </#if>
    </#list>
        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
                ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
                ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        //生成一个新的id
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }

    private void update(${Domain} ${domain}){
        <#list fieldList as field>
            <#if field.nameHump=='updatedAt'>
                ${domain}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    public void delete(String id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}