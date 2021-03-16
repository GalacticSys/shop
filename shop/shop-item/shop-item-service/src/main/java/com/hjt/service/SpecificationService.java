package com.hjt.service;


import com.hjt.item.pojo.SpecGroup;
import com.hjt.item.pojo.SpecParam;
import com.hjt.item.pojo.Specification;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/28 0:03
 */

public interface SpecificationService {

    List<SpecGroup> queryGroupByCid(Long cid);

    void insert(Long categoryId,List<SpecGroup> specGroups);

    void update(Long categoryId,List<SpecGroup> specGroups);

    List<SpecParam> getParams(Long cid);

    List<SpecParam> queryParams(Long gid,Long cid,Boolean generic,Boolean searching);

    List<SpecGroup> queryGroupsWithParam(Long cid);
}
