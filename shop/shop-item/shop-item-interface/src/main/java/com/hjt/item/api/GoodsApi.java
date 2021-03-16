package com.hjt.item.api;

import com.hjt.item.pojo.Sku;
import com.hjt.item.pojo.Spu;
import com.hjt.item.pojo.SpuDetail;
import com.hjt.item.pojo.bo.SpuBo;
import com.hjt.pojo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/2/26 21:25
 */
@RequestMapping("goods")
public interface GoodsApi {


    /**
     * 分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * 根据spuId查询spuDetail
     *
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public SpuBo findSpuBoBySpuId(@PathVariable Long id);

    /**
     * 根据spuId查询sku集合
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("spuId")Long spuId);


    /**
     * 根据spuId查询spu
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Spu querySpuBySpuId(@PathVariable("id")Long id);
}
