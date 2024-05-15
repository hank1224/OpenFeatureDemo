package com.example.openfeaturedemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "myapp")
public class MyAppConfig {
    private String name;
    private String description;

}