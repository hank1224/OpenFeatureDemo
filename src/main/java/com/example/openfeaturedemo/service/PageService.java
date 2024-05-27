package com.example.openfeaturedemo.service;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PageService {

    private final DynamicFeatureFlagService featureFlagService;

    @Autowired
    public PageService(DynamicFeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    public String secretButton() {
        Client client = OpenFeatureAPI.getInstance().getClient();
        boolean isEnabled = client.getBooleanValue("secret_button", false);

        if (isEnabled) {
            // 啟用時的邏輯
            return "secret-button_enabled";
        } else {
            // 禁用時的邏輯
            return "secret-button_disabled";
        }
    }

    public String secretButton2() {
        System.out.println(featureFlagService.evaluateFeatureFlagDetails("flagsmith", "secret_button2", "secret-button_disabled"));
        // 假設您希望使用 flagsmith 作為 Provider
        return featureFlagService.evaluateFeatureFlag("flagsmith", "secret_button", "secret-button_disabled");
    }

}
