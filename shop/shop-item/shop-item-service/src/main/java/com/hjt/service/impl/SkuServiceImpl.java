package com.hjt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjt.item.pojo.Brand;
import com.hjt.mapper.BrandMapper;
import com.hjt.mapper.SkuMapper;
import com.hjt.pojo.PageResult;
import com.hjt.service.BrandService;
import com.hjt.service.SkuService;
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
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;


}
