package com.shop.search.shopsearch.pojo;

import com.hjt.item.pojo.Brand;
import com.hjt.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/2 17:58
 */
public class SearchResult extends PageResult<Goods> {

    private List<Map<String,Object>> categories;

    private List<Brand> brands;

    private List<Map<String,Object>> spec;




    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Map<String, Object>> getSpec() {
        return spec;
    }

    public void setSpec(List<Map<String, Object>> spec) {
        this.spec = spec;
    }


    public SearchResult(List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> spec) {
        this.categories = categories;
        this.brands = brands;
        this.spec = spec;
    }

    public SearchResult(Long total, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> spec) {
        super(total, items);
        this.categories = categories;
        this.brands = brands;
        this.spec = spec;
    }

    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> spec) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.spec = spec;
    }

    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
    }
}
