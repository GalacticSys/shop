package com.hjt.mapper;


import com.hjt.item.pojo.SpecParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/28 0:03
 */
public interface SpecParamMapper extends Mapper<SpecParam> {

    @Delete("DELETE  FROM tb_spec_param WHERE group_id=#{groupId}")
    Integer deleteSpecParamById(@Param("groupId")Long groupId);
}
