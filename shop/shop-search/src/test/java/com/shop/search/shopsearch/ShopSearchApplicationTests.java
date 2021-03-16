package com.shop.search.shopsearch;

import com.hjt.item.pojo.bo.SpuBo;
import com.hjt.pojo.PageResult;
import com.shop.search.shopsearch.client.GoodsClient;
import com.shop.search.shopsearch.pojo.Goods;
import com.shop.search.shopsearch.repository.GoodsRepository;
import com.shop.search.shopsearch.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopSearchApplication.class)
public class ShopSearchApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void test1() {

        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        Integer page = 1;
        Integer rows = 100;

        do {
            //分页查询spu,获取分页结果集
            PageResult<SpuBo> spuBoPageResult = goodsClient.querySpuByPage(null, null, page, rows);
            List<SpuBo> items = spuBoPageResult.getItems();
            //处理list<SpuBo>==>List<Goods>
            List<Goods> goodsList = items.stream().map(spuBo -> {
                try {
                    return searchService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            this.goodsRepository.saveAll(goodsList);
            rows = items.size();
            page++;
        }while (rows==100);

    }

}
