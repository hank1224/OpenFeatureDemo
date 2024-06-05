package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.exception.ConflictException;
import com.example.openfeaturedemo.exception.ResourceNotFoundException;
import com.example.openfeaturedemo.repository.GoodsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    模擬逐步轉換至新架構的過程，透過FeatBit來控制使用新舊架構流量的比例。
    此為舊架構：
        GET： 從H2查詢獲取資料
        POST：檢查唯一性後，存入H2
 */

@Primary // SpringBoot 會需要指定優先該使用的 Bean，沒加編譯過不了，已確認能依照Flag正確選擇。
@Service("oldGoodsService")
public class OldGoodsServiceImpl implements GoodsService {
    private final GoodsRepository goodsRepository;

    @Autowired
    public OldGoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Goods getGoodsByProductCode(String productCode) throws ResourceNotFoundException{
        Optional<Goods> goodsOptional = goodsRepository.findByProductCode(productCode);
        return goodsOptional.orElseThrow(() -> {
            return new ResourceNotFoundException("Goods with product code " + productCode + " not found.");
        });
    }

    @Override
    @Transactional
    public void saveGoods(Goods goods) {
        validateGoods(goods);

        // 檢查唯一性
        Optional<Goods> existingGoods = goodsRepository.findByProductCode(goods.getProductCode());
        if (existingGoods.isPresent()) {
            throw new ConflictException("Product code already exists.");
        }
        goodsRepository.save(goods);
    }

    /* Check if the goods object is valid for save method. */

    public static class GoodsValidationException extends RuntimeException {
        public GoodsValidationException(String message) {
            super(message);
        }
    }

    public static class BadRequestException extends GoodsValidationException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    public static class IllegalArgumentException extends GoodsValidationException {
        public IllegalArgumentException(String message) {
            super(message);
        }
    }

    private void validateGoods(Goods goods) {
        // 檢查必填欄位是否為空
        checkRequiredField(goods.getProductCode(), "Product code");
        checkRequiredField(goods.getGoodsName(), "Goods name");
        checkRequiredField(goods.getGoodsCategoryId(), "Goods category ID");

        if (goods.getIsOnSale() == null) {
            throw new BadRequestException("Goods sell status (isOnSale) is required.");
        }

        if (goods.getStockNum() == null) {
            throw new BadRequestException("Stock number is required.");
        }

        // 檢查數值範圍
        checkNonNegative(goods.getCostPrice(), "Cost price");
        checkNonNegative(goods.getSellingPrice(), "Selling price");
        checkNonNegative(goods.getStockNum(), "Stock number");

        // 檢查業務邏輯
        if (goods.getCostPrice() != null && goods.getSellingPrice() != null &&
                goods.getSellingPrice() < goods.getCostPrice()) {
            throw new IllegalArgumentException("Selling price cannot be smaller than cost price.");
        }
    }

    private void checkRequiredField(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException(fieldName + " is required.");
        }
    }

    private void checkNonNegative(Number value, String fieldName) {
        if (value != null && value.doubleValue() < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }
}

