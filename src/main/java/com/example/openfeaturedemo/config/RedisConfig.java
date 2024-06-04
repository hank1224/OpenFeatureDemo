package com.example.openfeaturedemo.config;

import com.example.openfeaturedemo.entity.Goods;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("127.0.0.1");
        config.setPort(6380);
        config.setPassword(""); // 放Redis的密碼，這裡暫時沒有設
        config.setDatabase(0);

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(4);
        poolConfig.setMaxTotal(3000);

        LettucePoolingClientConfiguration poolingClientConfig =
                LettucePoolingClientConfiguration.builder()
                        .commandTimeout(Duration.ofMillis(3000))
                        .poolConfig(poolConfig)
                        .build();

        return new LettuceConnectionFactory(config, poolingClientConfig);
    }

    @Bean
    public RedisTemplate<String, Goods> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Goods> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // 設置 key 的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 配置 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // 確保日期時間格式正確
        objectMapper.findAndRegisterModules();

        // 使用 GenericJackson2JsonRedisSerializer 並設置 ObjectMapper
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        // 設置 value 和 hash value 的序列化器
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        // 設置 hash key 的序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
