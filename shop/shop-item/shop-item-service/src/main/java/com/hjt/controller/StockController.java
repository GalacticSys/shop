package com.hjt.controller;

import com.hjt.service.SkuService;
import com.hjt.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:40
 */
@Controller
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockService stockService;

}
