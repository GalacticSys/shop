package com.hjt.item.api;

import com.hjt.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:40
 */
@RequestMapping("category")
public interface CategoryApi {



    /**
     * 根据父id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public List<Category> queryCategoryByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid);



    /**
     * 通过品牌id查询商品分类
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public List<Category> queryByBrandId(@PathVariable("bid") Long bid);


    /**
     * 根据ids查询分类名称
     * @param ids
     * @return
     */
    @GetMapping
    public List<String> queryNameByIds(@RequestParam("ids")List<Long> ids);
}
