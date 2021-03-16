package com.hjt.item.pojo.bo;

import com.hjt.item.pojo.Sku;
import com.hjt.item.pojo.Spu;
import com.hjt.item.pojo.SpuDetail;

import java.util.List;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/1/30 23:40
 */
public class SpuBo extends Spu {

    private String cname;

    private String bname;

    private List<Sku> skus;

    private SpuDetail spuDetail;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    @Override
    public String toString() {
        return "SpuBo{" +
                "cname='" + cname + '\'' +
                ", bname='" + bname + '\'' +
                ", skus=" + skus +
                ", spuDetail=" + spuDetail +
                '}';
    }
}
