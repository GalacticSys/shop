package com.shop.search.shopsearch.repository;

import com.shop.search.shopsearch.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/2/26 23:04
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
