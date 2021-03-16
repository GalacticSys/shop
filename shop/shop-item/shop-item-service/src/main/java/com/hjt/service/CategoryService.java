package com.hjt.service;

import com.hjt.item.pojo.Category;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:39
 */
public interface CategoryService {

    List<Category> queryCategoryByPid(Long pid);

    List<Category> queryByBrandId(Long bid);

    List<String> queryNamesByIds(List<Long> ids);
}
