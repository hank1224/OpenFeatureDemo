package com.example.openfeaturedemo;

import com.example.openfeaturedemo.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping(path = "/{productCode}")
    public ResponseEntity<Goods> getGoodsByProductCode(@PathVariable String productCode) {
        try {
            Goods goods = goodsService.getGoodsByProductCode(productCode);
            return new ResponseEntity<>(goods, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/")
    public void saveGoods(@RequestBody Goods goods) {
        goodsService.saveGoods(goods);
    }

}

