package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.dto.SecretButtonDTO;
import dev.openfeature.sdk.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PageService {

    /* DemoCase1: Secret-Button */
    public SecretButtonDTO getSecretButtonFlag() {
        Client client = OpenFeatureAPI.getInstance().getClient("flagsmith");
        boolean isEnabled = client.getBooleanValue("secret-button", false);

        // 可以通過 getDetails 方法獲取完整詳細信息
        FlagEvaluationDetails<Boolean> secretButtonDetail = client.getBooleanDetails("secret-button", false);

        // 一般情況用上面的（isEnable）就可以了，此為顯示整包詳細狀態而封裝
        return new SecretButtonDTO(
                isEnabled,
                secretButtonDetail.getFlagKey(),
                secretButtonDetail.getValue(),
                secretButtonDetail.getVariant(),
                secretButtonDetail.getReason(),
                secretButtonDetail.getErrorCode(),
                secretButtonDetail.getErrorMessage(),
                secretButtonDetail.getFlagMetadata()
        );
    }

    /* DemoCase2: Multi-Button */
    public String flagsmithButton() {  //Demo case 2: Multi-Button
        Client client = OpenFeatureAPI.getInstance().getClient("flagsmith");
        boolean isEnabled = client.getBooleanValue("flagsmith-button", false);

        if (isEnabled) {
            return "flagsmith-button_enabled";
        } else {
            return "flagsmith-button_disabled";
        }
    }

    /* DemoCase2: Multi-Button */
    public String featbitButton() {
        Client client = OpenFeatureAPI.getInstance().getClient("featbit");

        // FeaBit SDK 要求在評估功能標誌時需要一個評估上下文
        // The OpenFeature specification allows for an optional targeting key, but FeatBit requires a key for evaluation.
        EvaluationContext evalCtx = new ImmutableContext("user-key", new HashMap<String, Value>() {{
            put("name", new Value("user-name"));
            put("country", new Value("USA"));
        }});

        boolean isEnabled = client.getBooleanValue("featbit-button", false, evalCtx);

        if (isEnabled) {
            return "fitbit-button_enabled";
        } else {
            return "fitbit-button_disabled";
        }
    }
}
