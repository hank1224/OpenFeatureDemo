package com.example.openfeaturedemo.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String productCode;
    //業務上商品編號

    private String goodsName;

    private String goodsIntro;

    private String goodsCategoryId;
    // 業務上分類編號

    private String goodsCoverImg;

    private Integer costPrice;

    private Integer sellingPrice;

    private Integer stockNum;

    private Boolean goodsSellStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Boolean getGoodsSellStatus() {
        return goodsSellStatus;
    }

    public void setGoodsSellStatus(Boolean goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productCode=").append(productCode);
        sb.append(", goodsCategoryId=").append(goodsCategoryId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsIntro=").append(goodsIntro);
        sb.append(", goodsCoverImg=").append(goodsCoverImg);
        sb.append(", cost_pricePrice=").append(costPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", stockNum=").append(stockNum);
        sb.append(", goodsSellStatus=").append(goodsSellStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }


}
