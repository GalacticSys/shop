package com.shop.search.shopsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjt.item.pojo.Brand;
import com.hjt.item.pojo.Sku;
import com.hjt.item.pojo.SpecParam;
import com.hjt.item.pojo.Spu;
import com.hjt.item.pojo.bo.SpuBo;
import com.hjt.pojo.PageResult;
import com.shop.search.shopsearch.client.BrandClient;
import com.shop.search.shopsearch.client.CategoryClient;
import com.shop.search.shopsearch.client.GoodsClient;
import com.shop.search.shopsearch.client.SpecificationClient;
import com.shop.search.shopsearch.pojo.Goods;
import com.shop.search.shopsearch.pojo.SearchResult;
import com.shop.search.shopsearch.pojo.request.SearchRequest;
import com.shop.search.shopsearch.repository.GoodsRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/2/26 21:52
 */

@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods = new Goods();
        //根据分类id查询分类名称
        List<String> cnames = categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        //根据品牌id查询品牌名称
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        //根据spuid查询所有sku
        List<Sku> skus = goodsClient.querySkusBySpuId(spu.getId());
        List<Long> prices = new ArrayList<>();
        List<Map<String,Object>> skuMapList = new ArrayList<>();
        skus.forEach(sku -> {
            prices.add(sku.getPrice());

            Map<String,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("image",StringUtils.isEmpty(sku.getImages())?"":StringUtils.split(sku.getImages(),",")[0]);
            skuMapList.add(map);
        });
        //根据spu的cid3获取搜索的规格参数
        List<SpecParam> params = specificationClient.queryParams(null, spu.getCid3(), null, true);
        //根据spuId查询spuDetail
        SpuBo spuBoBySpuId = goodsClient.findSpuBoBySpuId(spu.getId());
        //把通用的规格参数值进行反序列化
        Map<String,Object> genericSpecMap = MAPPER.readValue(spuBoBySpuId.getSpuDetail().getgenericSpec(), new TypeReference<Map<String, Object>>() {});
        //把特殊的规格参数值进行反序列化
        Map<String,List<Object>> specialSpecMap = MAPPER.readValue(spuBoBySpuId.getSpuDetail().getSpecialSpec(), new TypeReference<Map<String, Object>>() {});

        Map<String,Object> specs = new HashMap<>();
        params.forEach(param->{
            //判断规格参数类型是否是通用的规格参数
            if (param.getGeneric()){
                String value ="";
                try {
                    //如果是通用类型的参数，从genericMap获取规格参数值
                     value = genericSpecMap.get(param.getId().toString()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                判断是否是数值类型，如果是数值类型，应该返回一个区间
                if (param.getNumeric()){
                    value = chooseSegment(value, param);
                }
                specs.put(param.getName(),value);
            }else {
                //如果是特殊的规格参数，从specialSpecMap中获取值
                List<Object> value = specialSpecMap.get(param.getId().toString());
                specs.put(param.getName(),value);
            }
        });

        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(spu.getTitle()+" "+ StringUtils.join(cnames," ") +" "+brand.getName());
        //获取spu下的所有sku的价格
        goods.setPrice(prices);
        //获取spu下的所有sku,并转化成为json字符串
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        //获取spu下的所有规格参数
        goods.setSpecs(specs);
        return goods;
    }


    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }


    public SearchResult search(SearchRequest request){
        String key = request.getKey();
        // 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加查询条件
        //QueryBuilder basicQuery = QueryBuilders.matchQuery("all", key).operator(Operator.AND);
        BoolQueryBuilder basicQuery = buildBoolQueryBuilder(request);
        queryBuilder.withQuery(basicQuery);

        // 2、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id","skus","subTitle"}, null));
        // 3、分页
        // 准备分页参数
        int page = request.getPage();
        int size = request.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        //添加分类和品牌的聚合
        String categoryAggName = "categories";
        String brandAggName = "brands";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));


        // 执行查询，获取结果
        AggregatedPage<Goods> aggregatedPage = (AggregatedPage<Goods>)this.goodsRepository.search(queryBuilder.build());

        //获取聚合结果并解析
        List<Map<String,Object>> categories = getCategoryAggResult(aggregatedPage.getAggregation(categoryAggName));

        List<Brand> brands = getBrandAggResult(aggregatedPage.getAggregation(brandAggName));


        //判断是否是一个分类，只有一个分类时，才做规格参数的聚合
        List<Map<String,Object>> spec = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categories)&&categories.size()==1){
             spec = getParamAggResult((Long)categories.get(0).get("id"),basicQuery);
        }
        // 封装结果并返回
        return new SearchResult(aggregatedPage.getTotalElements(), new Long(aggregatedPage.getTotalPages()), aggregatedPage.getContent(),categories,brands,spec);
    }

    /**
     * 解析分类的结果集
     * @param aggregation
     * @return
     */
    public List<Map<String,Object>> getCategoryAggResult(Aggregation aggregation){
        LongTerms terms = (LongTerms) aggregation;

        //获取聚合中的桶,转化为List<Map<String,Object>>
        return terms.getBuckets().stream().map(bucket -> {
            Map<String, Object> map = new HashMap<>();
            long id = bucket.getKeyAsNumber().longValue();
            List<String> names = categoryClient.queryNameByIds(Arrays.asList(id));
            map.put("id", id);
            map.put("name", names.get(0));
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 解析品牌的结果集
     * @param aggregation
     * @return
     */
    public List<Brand> getBrandAggResult(Aggregation aggregation){
        LongTerms terms = (LongTerms) aggregation;

        //获取聚合中的桶
        return terms.getBuckets().stream().map(bucket -> {return this.brandClient.queryBrandById(bucket.getKeyAsNumber().longValue());}).collect(Collectors.toList());

    }


    /**
     * 根据查询条件聚合规格参数
     * @param cid
     * @param basicQuery
     * @return
     */
    private List<Map<String,Object>> getParamAggResult(Long cid,QueryBuilder basicQuery){
        //自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加查询基本条件
        queryBuilder.withQuery(basicQuery);

        //查询要聚合的规格参数
        List<SpecParam> params = specificationClient.queryParams(null, cid, null, true);

        //添加规格参数的聚合
        params.forEach(
                param->{
                    if (param.getSearching()){
                        queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs."+param.getName()+".keyword"));
                    }
                }
        );
        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行查询
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)goodsRepository.search(queryBuilder.build());
        //解析聚合结果集
        Map<String, Aggregation> stringAggregationMap = goodsPage.getAggregations().asMap();
        List<Map<String,Object>> specs = new ArrayList<>();
        for (Map.Entry<String,Aggregation> entry:stringAggregationMap.entrySet()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("k",entry.getKey());

            //初始化一个options集合，收集桶中的key
            List<String> options = new ArrayList<>();
            //获取集合
            StringTerms terms = (StringTerms)entry.getValue();
            //获取桶集合
            terms.getBuckets().forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });
            map.put("options",options);
            specs.add(map);
        }
        return specs;
    }


    /**
     * 构建布尔查询
     * @param request
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(SearchRequest request){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //添加基本查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all",request.getKey()).operator(Operator.AND));
        //添加过滤条件
        Map<String,Object> filter = request.getFilter();
        for (Map.Entry<String, Object> stringObjectEntry : filter.entrySet()) {
            String key = stringObjectEntry.getKey();
            if (StringUtils.equals("brandId",key)){
                key="brandId";
            }else if (StringUtils.equals("cid3",key)){
                key = "cid3";
            }else {
                key = "specs."+key+".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termQuery(key,stringObjectEntry.getValue()));
        }

        return boolQueryBuilder;
    }

    public void save(Long id) throws IOException {
        Spu spu = goodsClient.querySpuBySpuId(id);
        Goods goods = this.buildGoods(spu);
        goodsRepository.save(goods);
    }

    public void delete(Long id) {
        goodsRepository.deleteById(id);
    }
}
