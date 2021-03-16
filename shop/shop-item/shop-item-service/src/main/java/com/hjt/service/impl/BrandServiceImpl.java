package com.hjt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjt.item.pojo.Brand;
import com.hjt.mapper.BrandMapper;
import com.hjt.pojo.PageResult;
import com.hjt.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:39
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化Example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //根据name模糊查询或根据首字母查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        //添加分页条件
        PageHelper.startPage(page,rows);
        if (rows==-1){
            PageHelper.startPage(page,0);
        }else {
            PageHelper.startPage(page,rows);
        }
        //添加排序条件
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy +" " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Transactional
    @Override
    public void saveBrand(Brand brand,List<Long> cIds){
        //先新增Brand
        Boolean flag = this.brandMapper.insertSelective(brand)== 1;

        if (flag){
            cIds.forEach(cId->{
                this.brandMapper.insertCategoryAndBrand(cId,brand.getId());
                    }
            );
        }
    }

    @Override
    public Integer deleteBrandById(Long id) {
        return brandMapper.deleteBrandById(id);
    }

    @Override
    public List<Brand> queryBrandByCid(Long cid) {

        return brandMapper.selectBrandByCid(cid);
    }

    @Override
    public Brand queryBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
