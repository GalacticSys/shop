package com.hjt.item.api;

import com.hjt.item.pojo.Brand;
import com.hjt.pojo.PageResult;
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
 * @date 2020/12/3 4:40
 */
@Controller
@RequestMapping("brand")
public interface BrandApi {



    /**
     * 根据查询条件分页查询品牌
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public PageResult<Brand> queryBrandsByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
    );



    @GetMapping("cid/{cid}")
    public List<Brand> queryBrandByCid(@PathVariable("cid")Long cid);

    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);
}
