package com.hjt.service;

import com.hjt.item.pojo.Brand;
import com.hjt.pojo.PageResult;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:39
 */
public interface BrandService {

    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc );

    void saveBrand(Brand brand,List<Long> cIds);

    Integer deleteBrandById(Long id);

    List<Brand> queryBrandByCid(Long cid);

    Brand queryBrandById(Long id);
}
