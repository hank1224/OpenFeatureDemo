package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.annotation.DynamicApiSwitcher;
import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @DynamicApiSwitcher
    @GetMapping(path = "/{productCode}")
    @Operation(summary = "Get a Product by its ProductCode", description = "Using the ProductCode in business to obtain a product instead of using the ID.")
    public ResponseEntity<Goods> getGoodsByProductCode(@PathVariable String productCode) {
        Goods goods = goodsService.getGoodsByProductCode(productCode);
        return ResponseEntity.status(HttpStatus.OK).body(goods);
    }
    @Operation(summary = "Add an new Good")
    @DynamicApiSwitcher
    @PostMapping(path = "/")
    public ResponseEntity<String> saveGoods(@RequestBody @Valid Goods goods) {
        goodsService.saveGoods(goods);
        return ResponseEntity.status(HttpStatus.OK).body("Goods saved successfully");
    }
}

