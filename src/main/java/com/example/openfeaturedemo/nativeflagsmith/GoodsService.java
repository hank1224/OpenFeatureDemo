package com.example.openfeaturedemo.nativeflagsmith;

import org.springframework.stereotype.Service;

@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public Goods saveGoods(Goods goods) {
        goodsRepository.findByProductCode(goods.getProductCode())
                .ifPresent(g -> {
                    throw new IllegalStateException("Product code already exists.");
                });
        return goodsRepository.save(goods);
    }

    public Goods getGoodsByProductCode(String productCode) {
        return goodsRepository.findByProductCode(productCode)
                .orElseThrow(() -> new IllegalStateException("Product code does not exist."));
    }
}