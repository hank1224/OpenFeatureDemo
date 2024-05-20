package com.example.openfeaturedemo.Service;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.stereotype.Service;

@Service
public class PageService {
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

}
