package com.example.openfeaturedemo.config;

import co.featbit.openfeature.FBProvider;
import co.featbit.server.FBConfig;
import dev.openfeature.contrib.providers.flagsmith.FlagsmithProvider;
import dev.openfeature.contrib.providers.flagsmith.FlagsmithProviderOptions;
import dev.openfeature.sdk.OpenFeatureAPI;
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
    public FBProvider featbitProvider() {
        FBConfig featbitConfig = new FBConfig.Builder()
                .streamingURL(featbitStreamingURL)
                .eventURL(featbitEventURL)
                .build();

        FBProvider provider = new FBProvider(featbitEnvSecret, featbitConfig);

        //    註冊 provider 並指定名稱
        System.out.println("Registering featbit provider");
        OpenFeatureAPI.getInstance().setProvider("featbit", provider);
        return provider;
    }


    @Value("${flagsmith.api-key}")
    private String flagsmithApiKey;
    @Bean
    public FlagsmithProvider flagsmithProvider() {
        FlagsmithProviderOptions flagsmithOptions = FlagsmithProviderOptions.builder()
                .apiKey(flagsmithApiKey)
                .build();
        FlagsmithProvider provider = new FlagsmithProvider(flagsmithOptions);

        // 註冊 provider 並指定名稱
        System.out.println("Registering flagsmith provider");
        OpenFeatureAPI.getInstance().setProvider("flagsmith", provider);
        return provider;
    }
}