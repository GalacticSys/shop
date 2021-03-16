package com.hjt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjt.item.pojo.*;
import com.hjt.item.pojo.bo.SpuBo;
import com.hjt.mapper.*;
import com.hjt.pojo.PageResult;
import com.hjt.service.CategoryService;
import com.hjt.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/30 1:52
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;


    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //添加查询条件
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }

        //添加上下架的过滤条件
        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //添加分页
        PageHelper.startPage(page,rows);
        //执行查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        //spu集合转化成Spubo集合
        List<SpuBo> collect = spus.stream().map(
                spu -> {
                    SpuBo spuBo = new SpuBo();
                    BeanUtils.copyProperties(spu, spuBo);
                    //查询品牌名称
                    Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
                    spuBo.setBname(brand.getName());
                    //查询分类名称
                    List<String> names = categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

                    spuBo.setCname(StringUtils.join(names, "-"));
                    return spuBo;
                }
        ).collect(Collectors.toList());
        //返回pageResult<spuBo>集合
        return new PageResult<>(spuPageInfo.getTotal(),collect);
    }

    /**
     * 新增商品
     * @param spuBo
     */
    @Override
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        //新增spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insertSelective(spuBo);
        //新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.insertSelective(spuDetail);

        //新增sku和stock
        saveSkuAndStock(spuBo);
        sendMsg("insert",spuBo.getId());
    }

    private void sendMsg(String type,Long id) {
        try {
            amqpTemplate.convertAndSend("item."+type,id);
        }catch (AmqpException e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateGoods(SpuBo spuBo){

        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        List<Sku> skus = skuMapper.select(sku);
        //刪除stock
        skus.forEach(
                sku1 -> {
                    stockMapper.deleteByPrimaryKey(sku1.getId());
                }
        );
        //刪除sku
        Sku record = new Sku();
        record.setSpuId(spuBo.getId());
        skuMapper.delete(record);

        //新增sku和stock
        saveSkuAndStock(spuBo);

        //更新spu
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        //更新spuDetail

        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
        sendMsg("update",spuBo.getId());
    }

    /**
     * 新增sku和stock
     * @param spuBo
     */
    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(
                skuInfo -> {
                    //新增sku
                    skuInfo.setId(null);
                    skuInfo.setSpuId(spuBo.getId());
                    skuInfo.setCreateTime(new Date());
                    skuInfo.setLastUpdateTime(skuInfo.getCreateTime());
                    this.skuMapper.insertSelective(skuInfo);
                    //新增stock
                    Stock stock = new Stock();
                    stock.setSkuId(skuInfo.getId());
                    stock.setStock(skuInfo.getStock());
                    stockMapper.insertSelective(stock);
                }
        );
    }

    public SpuBo findSpuBoById(Long id){
        SpuBo spuBo = new SpuBo();
        //查询spu
        Spu spu =  this.spuMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(spu,spuBo);
        //查询sku
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skus = skuMapper.select(sku);
        skus.forEach(
                sku1 -> {
                    Stock stock = stockMapper.selectByPrimaryKey(sku1.getId());
                    sku1.setStock(stock.getStock());
                }
        );
        spuBo.setSkus(skus);
        //查询spuDetail
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        spuBo.setSpuDetail(spuDetail);
        System.out.println("abc");
        System.out.println(spuBo.toString());
        return spuBo;
    }

    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        return skuMapper.select(sku);
    }

    @Override
    public Spu querySpuBySpuId(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        return spu;
    }
}
