package com.hjt.item.api;

import com.hjt.item.pojo.SpecGroup;
import com.hjt.item.pojo.SpecParam;
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
@RequestMapping("spec")
public interface SpecificationApi {


    /**
     * 根据条件查询规格参数
     *
     * @param gid
     * @return
     */
    @GetMapping("params")
    public List<SpecParam> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * 根据分类id查询参数组
     *
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid);


    /**
     * 根据分类id查询参数列表
     *
     * @param cid
     * @return
     */
    @GetMapping("/param/{cid}")
    public ResponseEntity<List<SpecParam>> getParams(@PathVariable("cid") Long cid);


    /**
     * 根据分类id查询规格组和规格组下的规格
     * @param cid
     * @return
     */
    @GetMapping("group/param/{cid}")
    public List<SpecGroup> queryGroupsWithParam(@PathVariable("cid")Long cid);
}
