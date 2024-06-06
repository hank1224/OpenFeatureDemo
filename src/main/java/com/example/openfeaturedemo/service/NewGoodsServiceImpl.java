package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.exception.BadRequestException;
import com.example.openfeaturedemo.exception.ConflictException;
import com.example.openfeaturedemo.exception.ResourceNotFoundException;
import com.example.openfeaturedemo.repository.GoodsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

// TODO: POST方法與舊的無異，完全重疊，可以考慮抽取出來。

/*
    模擬逐步轉換至新架構的過程，透過FeatBit來控制使用新舊架構流量的比例。
    此為新架構：
        GET： 
            1. 查詢Redis緩存，回傳結果（查無則下一步）
            2. 從H2查詢獲取資料，存入Redis緩存
            3. 回傳結果
        POST：
            檢查唯一性後，存入H2
 */

@Service("newGoodsService")
public class NewGoodsServiceImpl implements GoodsService {
    private final GoodsRepository goodsRepository;

    private final RedisTemplate<String, Goods> redisTemplate;

    @Autowired
    public NewGoodsServiceImpl(GoodsRepository goodsRepository, RedisTemplate<String, Goods> redisTemplate) {
        this.goodsRepository = goodsRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Goods getGoodsByProductCode(String productCode) {
        String redisProductCodeKey = "goodsProductCode:" + productCode;
        Goods goods = redisTemplate.opsForValue().get(redisProductCodeKey);
        if (goods == null) {
            goods = goodsRepository.getGoodsByProductCode(productCode);
            if (goods == null) {
                throw new ResourceNotFoundException("Goods with product code " + productCode + " not found.");
            }
            // 寫入Redis緩存並設定TTL
            redisTemplate.opsForValue().set(redisProductCodeKey, goods, 10, TimeUnit.SECONDS);
        }
        return goods;
    }

    @Override
    @Transactional
    public void saveGoods(Goods goods) {
        validateGoods(goods);

        // 檢查唯一性
        if (goodsRepository.existsByProductCode(goods.getProductCode())) {
            throw new ConflictException("Product code already exists.");
        }
        goodsRepository.save(goods);
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
            throw new BadRequestException("Selling price cannot be smaller than cost price.");
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
