package com.shop.search.shopsearch.client;

import com.hjt.item.api.BrandApi;
import com.hjt.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/2/26 21:16
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {

}
