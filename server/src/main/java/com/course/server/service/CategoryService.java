package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
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
 * class:CategoryController
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
                categoryExample.setOrderByClause("sort asc");

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
//        将查出来的categorylist传到分页里面去
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
//      gettotal获取总行数
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(categoryDtoList);
    }

    public List<CategoryDto> all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        return categoryDtoList;
    }

    public void save(CategoryDto categoryDto){
        Category category = CopyUtil.copy(categoryDto,Category.class);
        if (StringUtils.isEmpty(category.getId())) {
            this.insert(category);
        } else {
            this.update(category);
        }
    }

    private void insert(Category category){

        //生成一个新的id
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    private void update(Category category){
        categoryMapper.updateByPrimaryKey(category);
    }

    @Transactional
    public void delete(String id){
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除子分类
     * @param id
     */
    public void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            // 如果是一级分类，需要删除其下的二级分类
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(example);
        }
    }
}