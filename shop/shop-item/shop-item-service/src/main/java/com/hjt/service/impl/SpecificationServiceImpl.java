package com.hjt.service.impl;


import com.hjt.item.pojo.SpecGroup;
import com.hjt.item.pojo.SpecParam;
import com.hjt.mapper.SpecGroupMapper;
import com.hjt.mapper.SpecParamMapper;
import com.hjt.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/28 0:03
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        SpecParam specParam = new SpecParam();
        specGroup.setCid(cid);
        List<SpecGroup> select = specGroupMapper.select(specGroup);
        select.forEach(
                specGroup1 -> {
                    specParam.setGroupId(specGroup1.getId());
                    List<SpecParam> select1 = specParamMapper.select(specParam);
                    specGroup1.setParams(select1);
                }
        );
        return select;
    }

    @Override
    @Transactional
    public void insert(Long categoryId, List<SpecGroup> specGroups) {
        specGroups.forEach(
                specGroup -> {
                        specGroup.setCid(categoryId);
                        this.specGroupMapper.insert(specGroup);
                        specGroup.getParams().forEach(
                                specParam -> {
                                    specParam.setGroupId(specGroup.getId());
                                    specParam.setCid(categoryId);
                                    specParamMapper.insert(specParam);
                                }
                        );
                }
        );
    }

    @Override
    @Transactional
    public void update(Long categoryId, List<SpecGroup> specGroups) {
        specGroups.forEach(
              specGroup -> {
                  specGroupMapper.delete(specGroup);
                  specGroup.getParams().forEach(
                          specParam -> {
                            this.specParamMapper.deleteSpecParamById(specGroup.getId());
                          }
                  );
              }
        );
    this.insert(categoryId,specGroups);
    }

    @Override
    public List<SpecParam> getParams(Long cid) {
        SpecParam record = new SpecParam();
        record.setCid(cid);
        List<SpecParam> select = specParamMapper.select(record);
        return select;
    }

    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        return specParamMapper.select(specParam);
    }

    @Override
    public List<SpecGroup> queryGroupsWithParam(Long cid) {
        List<SpecGroup> groups = queryGroupByCid(cid);
        groups.forEach(group->{
            List<SpecParam> params = queryParams(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;


    }


}
