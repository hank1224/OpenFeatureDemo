package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.dto.MultiButtonDTO;
import com.example.openfeaturedemo.dto.SecretButtonDTO;
import dev.openfeature.sdk.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PageService {

    /* DemoCase1: Secret-Button */
    public SecretButtonDTO getSecretButtonFlag() {

        // Named in config/FeatureFlagConfig.java
        Client client = OpenFeatureAPI.getInstance().getClient("flagsmith");

        // "false" serves as a default value in case the flag value retrieval fails.
        boolean isEnabled = client.getBooleanValue("secret-button", false);

        // 也可以使用 getDetails 方法獲取詳細訊息
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
    public MultiButtonDTO getMultiButtonFlag() {
        /* Flagsmith */
        Client flagsmithClient = OpenFeatureAPI.getInstance().getClient("flagsmith");
        boolean flagsmithButtonIsEnable = flagsmithClient.getBooleanValue("flagsmith-button", false);

        /* FeatBit */
        Client featbitClient = OpenFeatureAPI.getInstance().getClient("featbit");
        // The OpenFeature specification allows for an optional targeting key, but FeatBit requires a key for evaluation.
        EvaluationContext evalCtx = new ImmutableContext("user-key", new HashMap<String, Value>() {{
            put("name", new Value("OpenFeatureDemo"));
        }});
        boolean featbitButtonIsEnable = featbitClient.getBooleanValue("featbit-button", false, evalCtx);

        return new MultiButtonDTO(flagsmithButtonIsEnable, featbitButtonIsEnable);
    }
}
