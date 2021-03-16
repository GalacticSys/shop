package com.hjt.controller;

import com.alibaba.fastjson.JSONArray;
import com.hjt.item.pojo.SpecGroup;
import com.hjt.item.pojo.SpecParam;
import com.hjt.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/28 0:07
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;



    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    ){
        List<SpecParam> params = specificationService.queryParams(gid, cid, generic, searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }
    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }


    /**
     * 为分类添加分组
     * @param categoryId
     * @param specGroups
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insert(Long categoryId,String specGroups) {
        List<SpecGroup> specGroupList = JSONArray.parseArray(specGroups, SpecGroup.class);
        this.specificationService.insert(categoryId,specGroupList);
        System.out.println("aa");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改分类的属性分组与分组的属性名称
     * @param categoryId
     * @param specGroups
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> update(Long categoryId,String specGroups) {
        List<SpecGroup> specGroupList = JSONArray.parseArray(specGroups, SpecGroup.class);
        this.specificationService.update(categoryId,specGroupList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    /**
     * 根据分类id查询参数列表
     * @param cid
     * @return
     */
    @GetMapping("/param/{cid}")
    public ResponseEntity<List<SpecParam>> getParams(@PathVariable("cid")Long cid){
        List<SpecParam> params = this.specificationService.getParams(cid);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    @GetMapping("group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsWithParam(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = specificationService.queryGroupsWithParam(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }
}
