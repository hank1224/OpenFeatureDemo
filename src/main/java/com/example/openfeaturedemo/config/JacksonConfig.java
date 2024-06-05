package com.example.openfeaturedemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    用於創建 Goods 的 Redis Cache，並將 Goods 實體序列化為 JSON 字符串。
    序列化 Goods 實體時，OffsetDateTime 類型的屬性 createTime 無法被序列化為 JSON。
    這是因為 Jackson 默認不支持 Java 8 的日期和時間類型。為了解決這個問題，我們需要註冊 Java Time 模組。
 */

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 註冊 Java Time 模組
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
