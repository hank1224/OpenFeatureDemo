package com.example.openfeaturedemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(unique = true, nullable = false) // 當你試圖將數據保存到數據庫時，數據庫會強制執行這些約束
    @Schema(description = "業務上商品編號", example = "ipad2025")
    @NotNull(message = "Product code is required.") //當你接收數據（例如從HTTP請求中接收數據）或在應用程序內部傳遞數據時，會進行這些驗證。
    @Size(min = 1, message = "Product code is required.")
    private String productCode;

    @Schema(description = "商品名稱", example = "iPad 2025")
    @NotNull(message = "Goods name is required.")
    @Size(min = 1, message = "Goods name is required.")
    private String goodsName;

    @Schema(description = "商品介紹", example = "2025年最新款iPad")
    private String goodsIntro;

    @Schema(description = "業務上分類編號", example = "ipad")
    @NotNull(message = "Goods category ID is required.")
    private String goodsCategoryId;

    @Schema(description = "商品封面圖片", example = "https://www.example.com/ipad2025.jpg")
    private String goodsCoverImg;

    @Schema(description = "成本價", example = "10000")
    @PositiveOrZero(message = "Cost price cannot be negative.")
    private Integer costPrice;

    @Schema(description = "售價", example = "12000")
    @PositiveOrZero(message = "Selling price cannot be negative.")
    private Integer sellingPrice;

    @Schema(description = "庫存數量", example = "100")
    @PositiveOrZero(message = "Stock number cannot be negative.")
    private Integer stockNum;

    @Schema(description = "商品上下架狀態")
    @NotNull(message = "Goods sell status (isOnSale) is required.")
    private Boolean isOnSale;

    @Schema(description = "建立時間", hidden = true)
    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createTime;

    @Schema(description = "更新時間", hidden = true)
    private OffsetDateTime updateTime;

    // getter and setter

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

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(OffsetDateTime updateTime) {
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
        sb.append(", costPrice=").append(costPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", stockNum=").append(stockNum);
        sb.append(", isOnSale=").append(isOnSale);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }


}
