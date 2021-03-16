package com.shop.listener;

import com.shop.service.GoodsHtmlService;
import com.shop.service.GoodsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/13 22:40
 */
@Component
public class GoodsListener {

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "SHOP.ITEM.SAVE.QUEUE",durable = "true"),
            exchange = @Exchange(value = "SHOP.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void save(Long id){
        if (id==null){
            return;
        }
        this.goodsHtmlService.createHtml(id);
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
        this.goodsHtmlService.deleteHtml(id);
    }
}
