package com.shop.search.shopsearch.listener;

import com.shop.search.shopsearch.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/13 22:40
 */
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            //绑定队列
            value = @Queue(value = "SHOP.ITEM.SAVE.QUEUE",durable = "true"),
            //绑定交换机
            exchange = @Exchange(value = "SHOP.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            //绑定路由
            key = {"item.insert","item.update"}
    ))
    public void save(Long id) throws IOException {
        if (id==null){
            return;
        }
        this.searchService.save(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "SHOP.ITEM.DELETE.QUEUE",durable = "true"),
            exchange = @Exchange(value = "SHOP.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.DELETE"}
    ))
    public void delete(Long id){
        if (id==null){
            return;
        }
        this.searchService.delete(id);
    }
}
