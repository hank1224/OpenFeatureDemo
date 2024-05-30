package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/goods")
@Tag(name = "Goods API", description = "simple goods CRUD for Demo use.")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping(path = "/{productCode}")
    @Operation(summary = "Get a Product by its ProductCode", description = "Using the ProductCode in business to obtain a product instead of using the ID.")
    public ResponseEntity<Goods> getGoodsByProductCode(@PathVariable String productCode) {
        try {
            Goods goods = goodsService.getGoodsByProductCode(productCode);
            return new ResponseEntity<>(goods, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/")
    @Operation(summary = "Add an new Good")
    public void saveGoods(@ParameterObject @RequestBody @Valid Goods goods) {
        goodsService.saveGoods(goods);
    }
}

