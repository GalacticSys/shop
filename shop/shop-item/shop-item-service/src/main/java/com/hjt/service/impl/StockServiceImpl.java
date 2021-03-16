package com.hjt.service.impl;

import com.hjt.mapper.SkuMapper;
import com.hjt.mapper.StockMapper;
import com.hjt.service.SkuService;
import com.hjt.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 4:39
 */
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper StockMapper;


}
