package com.shop.search.shopsearch.client;

import com.hjt.item.api.GoodsApi;
import com.hjt.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/2/26 21:16
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
