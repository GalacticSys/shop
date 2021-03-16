package com.hjt.controller;

import com.hjt.item.pojo.Brand;
import com.hjt.pojo.PageResult;
import com.hjt.service.BrandService;
import com.hjt.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:40
 */
@Controller
@RequestMapping("sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

}
