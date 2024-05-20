package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;
    @Autowired
    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public Goods getGoodsByProductCode(String productCode) {
        return goodsRepository.findByProductCode(productCode)
                .orElseThrow(() -> new IllegalStateException("Product code does not exist."));
    }
    
    public void saveGoods(Goods goods) {
        goodsRepository.save(goods);
    }
}