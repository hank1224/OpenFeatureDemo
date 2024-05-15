package com.example.openfeaturedemo.nativeflagsmith.config;

import com.example.openfeaturedemo.nativeflagsmith.entity.Goods;
import com.example.openfeaturedemo.nativeflagsmith.repository.GoodsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class GoodsConfig {
    @Bean
    CommandLineRunner goodsCommandLineRunner(GoodsRepository goodsRepository) {
        return args -> {
            Goods goods1 = new Goods();
            goods1.setProductCode("iphone13promax");
            goods1.setGoodsName("iPhone 13 Pro Max");
            goods1.setGoodsIntro("我的手機超牛逼");
            goods1.setGoodsCategoryId("phone");
            goods1.setGoodsCoverImg("coverImgUrl1");
            goods1.setOriginalPrice(33500);
            goods1.setSellingPrice(36900);
            goods1.setStockNum(100);
            goods1.setGoodsSellStatus(true);
            goods1.setCreateTime(new Date());
            

            Goods goods2 = new Goods();
            goods2.setProductCode("ipadpro2021");
            goods2.setGoodsName("iPad Pro 2021");
            goods2.setGoodsIntro("我的平板超牛逼");
            goods2.setGoodsCategoryId("laptop");
            goods2.setGoodsCoverImg("coverImgUrl2");
            goods2.setOriginalPrice(34900);
            goods2.setSellingPrice(38000);
            goods2.setStockNum(20);
            goods2.setGoodsSellStatus(true);
            goods2.setCreateTime(new Date());

            // 保存到数据库
            goodsRepository.save(goods1);
            goodsRepository.save(goods2);
        };
    }
}
