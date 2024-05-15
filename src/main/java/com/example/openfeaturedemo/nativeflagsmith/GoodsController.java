package com.example.openfeaturedemo.nativeflagsmith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @RequestMapping(path = "/{productCode}")
    public ResponseEntity<Goods> getGoodsByProductCode(@PathVariable String productCode) {
        try {
            Goods goods = goodsService.getGoodsByProductCode(productCode);
            return new ResponseEntity<>(goods, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void saveGoods(Goods goods) {
        goodsService.saveGoods(goods);
    }

}

