package com.shop.service;

import com.hjt.item.pojo.*;
import com.hjt.item.pojo.bo.SpuBo;
import com.shop.client.BrandClient;
import com.shop.client.CategoryClient;
import com.shop.client.GoodsClient;
import com.shop.client.SpecificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/10 13:41
 */
@Service
public class GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;


    public Map<String,Object> loadData(Long spuId){
        Map<String,Object> model = new HashMap<>();

        //根据spuId查询spu
        Spu spu = goodsClient.querySpuBySpuId(spuId);

        //查询spuDetail
        SpuBo spuBoBySpuId = goodsClient.findSpuBoBySpuId(spuId);

        //查询分类
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = categoryClient.queryNameByIds(cids);
        //初始化一个分类map
        List<Map<String,Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",cids.get(i));
            map.put("name",names.get(i));
            categories.add(map);
        }

        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());

        //skus
        List<Sku> skus = goodsClient.querySkusBySpuId(spuId);

        //查询规格参数组
        List<SpecGroup> groups = specificationClient.queryGroupsWithParam(spu.getCid3());

        //查询特殊的规格参数
        List<SpecParam> params = specificationClient.queryParams(null, spu.getCid3(), false, null);
        Map<Long,String> paramMap = new HashMap<>();
        params.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });
        model.put("spu",spu);
        model.put("spuDetail",spuBoBySpuId.getSpuDetail());
        model.put("categories",categories);
        model.put("brand",brand);
        model.put("skus",skus);
        model.put("groups",groups);
        model.put("paramMap",paramMap);

        return model;
    }
}
