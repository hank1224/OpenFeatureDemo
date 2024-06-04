package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.exception.BadRequestException;
import com.example.openfeaturedemo.exception.ResourceNotFoundException;
import com.example.openfeaturedemo.exception.ConflictException;

public interface GoodsService {
    Goods getGoodsByProductCode(String productCode) throws ResourceNotFoundException;

    void saveGoods(Goods goods) throws BadRequestException, ConflictException;
}
