package com.hjt.mapper;

import com.hjt.item.pojo.Brand;
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

public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) values(#{cId},#{bId})")
    void insertCategoryAndBrand(@Param("cId") Long cId, @Param("bId") Long bId);


    @Delete("DELETE  FROM tb_brand WHERE id=#{id}")
    Integer deleteBrandById(@Param("id")Long id);

    @Select("SELECT * FROM tb_brand a INNER JOIN tb_category_brand b ON a.id=b.brand_id WHERE b.category_id=#{cid}")
    List<Brand> selectBrandByCid(@Param("cid") Long cid);
}
