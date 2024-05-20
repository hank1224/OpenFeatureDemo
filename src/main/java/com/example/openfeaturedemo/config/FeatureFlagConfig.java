package com.example.openfeaturedemo.config;

import dev.openfeature.contrib.providers.flagsmith.FlagsmithProvider;
import dev.openfeature.contrib.providers.flagsmith.FlagsmithProviderOptions;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagConfig {

    @Value("${flagsmith.key}")
    private String flagsmithKey;

    @Bean
    public FlagsmithProvider flagsmithProvider() {
        FlagsmithProviderOptions options = FlagsmithProviderOptions.builder()
                .apiKey(flagsmithKey)
                .build();

        FlagsmithProvider provider = new FlagsmithProvider(options); // 確保傳遞options
        OpenFeatureAPI.getInstance().setProvider(provider);
        return provider;
    }
}
