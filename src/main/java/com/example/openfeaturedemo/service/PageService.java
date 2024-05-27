package com.example.openfeaturedemo.service;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    public String secretButton() {
        Client client = OpenFeatureAPI.getInstance().getClient("flagsmith");
        boolean isEnabled = client.getBooleanValue("flagsmith-button", false);
        Object detail = client.getBooleanDetails("flagsmith-button", false);
        System.out.println(detail);

        if (isEnabled) {
            // 啟用時的邏輯
            return "secret-button_enabled";
        } else {
            // 禁用時的邏輯
            return "secret-button_disabled";
        }
    }

    public String secretButton2() {
        Client client = OpenFeatureAPI.getInstance().getClient("featbit");
        boolean isEnabled = client.getBooleanValue("featbit-button", false);
        Object detail = client.getBooleanDetails("featbit-button", false);
        System.out.println(detail);

        if (isEnabled) {
            // 啟用時的邏輯
            return "secret-button_enabled";
        } else {
            // 禁用時的邏輯
            return "secret-button_disabled";
        }
    }
}
