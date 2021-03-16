package com.shop.search.shopsearch.controller;

import com.hjt.pojo.PageResult;
import com.shop.search.shopsearch.pojo.Goods;
import com.shop.search.shopsearch.pojo.SearchResult;
import com.shop.search.shopsearch.pojo.request.SearchRequest;
import com.shop.search.shopsearch.repository.GoodsRepository;
import com.shop.search.shopsearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/2/27 0:06
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/page")
    public ResponseEntity<SearchResult> queryPage(@RequestBody SearchRequest searchRequest){
        SearchResult search = searchService.search(searchRequest);
        if (search == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(search);
    }
}
