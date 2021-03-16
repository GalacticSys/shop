package com.hjt.mapper;

import com.hjt.item.pojo.Brand;
import com.hjt.item.pojo.Sku;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:38
 */

public interface SkuMapper extends Mapper<Sku> {


}
