package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.exception.BadRequestException;
import com.example.openfeaturedemo.exception.ResourceNotFoundException;
import com.example.openfeaturedemo.exception.ConflictException;
import com.example.openfeaturedemo.repository.GoodsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary // SpringBoot 會需要指定優先該使用的 Bean，沒加這個過不了，但也不確定加這個會不會影響，永遠選舊的
@Service("oldGoodsService")
public class OldGoodsServiceImpl implements GoodsService {
    private final GoodsRepository goodsRepository;

    @Autowired
    public OldGoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Goods getGoodsByProductCode(String productCode) {
        return goodsRepository.findByProductCode(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product code does not exist."));
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

    private void validateGoods(Goods goods) {
        // 檢查必填欄位是否為空
        if (goods.getProductCode() == null || goods.getProductCode().trim().isEmpty()) {
            throw new BadRequestException("Product code is required.");
        }
        if (goods.getGoodsName() == null || goods.getGoodsName().trim().isEmpty()) {
            throw new BadRequestException("Goods name is required.");
        }
        if (goods.getGoodsCategoryId() == null || goods.getGoodsCategoryId().trim().isEmpty()) {
            throw new BadRequestException("Goods category ID is required.");
        }
        if (goods.getIsOnSale() == null) {
            throw new BadRequestException("Goods sell status(isOnSale) is required.");
        }
        if (goods.getStockNum() == null) {
            throw new BadRequestException("Stock number is required.");
        }

        // 檢查數值範圍
        if (goods.getCostPrice() != null && goods.getCostPrice() < 0) {
            throw new IllegalArgumentException("Cost price cannot be negative.");
        }
        if (goods.getSellingPrice() != null && goods.getSellingPrice() < 0) {
            throw new IllegalArgumentException("Selling price cannot be negative.");
        }
        if (goods.getStockNum() < 0) {
            throw new IllegalArgumentException("Stock number cannot be negative.");
        }

        // 檢查業務邏輯
        if (goods.getCostPrice() != null && goods.getSellingPrice() != null &&
                goods.getSellingPrice() < goods.getCostPrice()) {
            throw new IllegalArgumentException("Selling price cannot be smaller than cost price.");
        }
    }
}
