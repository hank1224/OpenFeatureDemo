package com.example.openfeaturedemo.config;

import co.featbit.openfeature.FBProvider;
import co.featbit.server.FBConfig;
import dev.openfeature.contrib.providers.flagsmith.FlagsmithProvider;
import dev.openfeature.contrib.providers.flagsmith.FlagsmithProviderOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagConfig {

    @Value("${featbit.streamingURL}")
    private String featbitStreamingURL;

    @Value("${featbit.eventURL}")
    private String featbitEventURL;

    @Value("${featbit.envSecret}")
    private String featbitEnvSecret;
    @Bean
    public FBProvider featBitProvider() {
        FBConfig featBitConfig = new FBConfig.Builder()
                .streamingURL(featbitStreamingURL)
                .eventURL(featbitEventURL)
                .build();
        return new FBProvider(featbitEnvSecret, featBitConfig);
    }

    @Value("${flagsmith.api-key}")
    private String flagsmithApiKey;
    @Bean
    public FlagsmithProvider flagsmithProvider() {
        FlagsmithProviderOptions flagsmithOptions = FlagsmithProviderOptions.builder()
                .apiKey(flagsmithApiKey)
                .build();
        return new FlagsmithProvider(flagsmithOptions);
    }
}