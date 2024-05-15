package com.example.openfeaturedemo.nativeflagsmith.config;

import com.example.openfeaturedemo.nativeflagsmith.entity.GoodsCategory;
import com.example.openfeaturedemo.nativeflagsmith.repository.GoodsCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class GoodsCategoryConfig {
    @Bean
    CommandLineRunner goodsCategoryCommandLineRunner(GoodsCategoryRepository goodsCategoryRepository) {
        return args -> {
            GoodsCategory goodsCategory1 = new GoodsCategory();
            goodsCategory1.setGoodsCategoryId("phone");
            goodsCategory1.setGoodsCategoryName("iPhone Series");
            goodsCategory1.setCreateTime(new Date());

            GoodsCategory goodsCategory2 = new GoodsCategory();
            goodsCategory2.setGoodsCategoryId("laptop");
            goodsCategory2.setGoodsCategoryName("iPad Series");
            goodsCategory2.setCreateTime(new Date());

            goodsCategoryRepository.save(goodsCategory1);
            goodsCategoryRepository.save(goodsCategory2);
        };
    }

}
