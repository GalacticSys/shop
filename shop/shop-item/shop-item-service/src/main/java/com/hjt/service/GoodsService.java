package com.hjt.service;

import com.hjt.item.pojo.Sku;
import com.hjt.item.pojo.Spu;
import com.hjt.item.pojo.bo.SpuBo;
import com.hjt.pojo.PageResult;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/30 1:52
 */
public interface GoodsService {

    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    void saveGoods(SpuBo spuBo);

    void updateGoods(SpuBo spuBo);

    SpuBo findSpuBoById(Long id);

    List<Sku> querySkusBySpuId(Long spuId);

    Spu querySpuBySpuId(Long id);
}
