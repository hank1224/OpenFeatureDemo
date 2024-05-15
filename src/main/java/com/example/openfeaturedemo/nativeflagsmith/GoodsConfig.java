package com.example.openfeaturedemo.nativeflagsmith;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class GoodsConfig {
    @Bean
    CommandLineRunner commandLineRunner(GoodsRepository repository) {
        return args -> {
            Goods goods1 = new Goods();
            goods1.setProductCode("productCode1");
            goods1.setGoodsName("productName1");
            goods1.setGoodsIntro("productIntro1");
            goods1.setGoodsCategoryId(1L);
            goods1.setGoodsCoverImg("coverImgUrl1");
            goods1.setOriginalPrice(100);
            goods1.setSellingPrice(80);
            goods1.setStockNum(100);
            goods1.setGoodsSellStatus((byte) 1);
            goods1.setCreateTime(new Date());
            goods1.setUpdateTime(new Date());

            Goods goods2 = new Goods();
            goods2.setProductCode("productCode2");
            goods2.setGoodsName("productName2");
            goods2.setGoodsIntro("productIntro2");
            goods2.setGoodsCategoryId(2L);
            goods2.setGoodsCoverImg("coverImgUrl2");
            goods2.setOriginalPrice(200);
            goods2.setSellingPrice(180);
            goods2.setStockNum(200);
            goods2.setGoodsSellStatus((byte) 2);
            goods2.setCreateTime(new Date());
            goods2.setUpdateTime(new Date());

            // 保存到数据库
            repository.save(goods1);
            repository.save(goods2);
        };
    }
}
