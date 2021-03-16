package com.shop.controller;

import com.shop.service.GoodsHtmlService;
import com.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/10 13:07
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){
        Map<String, Object> map = goodsService.loadData(id);
        model.addAllAttributes(map);
        goodsHtmlService.createHtml(id);

        return "item";
    }
}
