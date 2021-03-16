package com.hjt.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author li
 */
@Table(name="tb_spu_detail")
public class SpuDetail {
    @Id
    /**
     * 对应的SPU的id
     */
    private Long spuId;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品特殊规格的名称及可选值模板
     */
    private String specialSpec;
    /**
     * 商品的全局规格属性
     */
    private String genericSpec;
    /**
     * 包装清单
     */
    private String packingList;
    /**
     * 售后服务
     */
    private String afterService;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialSpec() {
        return specialSpec;
    }

    public void setSpecialSpec(String specTemplate) {
        this.specialSpec = specTemplate;
    }

    public String getgenericSpec() {
        return genericSpec;
    }

    public void setgenericSpec(String genericSpec) {
        this.genericSpec = genericSpec;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    @Override
    public String toString() {
        return "SpuDetail{" +
                "spuId=" + spuId +
                ", description='" + description + '\'' +
                ", specialSpec='" + specialSpec + '\'' +
                ", genericSpec='" + genericSpec + '\'' +
                ", packingList='" + packingList + '\'' +
                ", afterService='" + afterService + '\'' +
                '}';
    }
}
