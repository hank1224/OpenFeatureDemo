package com.example.openfeaturedemo.service;

import com.example.openfeaturedemo.dto.BeforeHookEmailCryptoRequestDTO;
import dev.openfeature.sdk.*;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FlagEvalService {
    public void beforeHookEmailCrypto(BeforeHookEmailCryptoRequestDTO flagEvalRequest) {

        Client featbitClient = OpenFeatureAPI.getInstance().getClient("featbit");
        featbitClient.addHooks(new BeforeHookEmailCryptoHook());
        // The OpenFeature specification allows for an optional targeting key, but FeatBit requires a key for evaluation.
        EvaluationContext evalCtx = new ImmutableContext("user-key", new HashMap<String, Value>() {{
            put("OpenFeatureDemoCase5", new Value("Server-side:" + (int) (Math.random() * 100)));
        }});
        boolean featbitButtonIsEnable = featbitClient.getBooleanValue("before-hook-email-crypto", false, evalCtx);

        if ("error".equals(flagEvalRequest.getEvalOutcome())) {
            // 將flagEvalRequest.userEmailInput 使用Before Hook進行 SHA-256 Hash 加密
            throw new RuntimeException("Flag evaluation failed");
        }
    }
}

public class BeforeHookEmailCryptoHook implements Hook<Boolean> {
    @Override
    public Optional<EvaluationContext> before(HookContext<Boolean> ctx, Map<String, Object> hints) {
        // 從評估上下文中獲取 userEmailInput
        String userEmailInput = (String) ctx.getCtx().
        if (userEmailInput != null) {
            try {
                // 使用SHA-256進行加密
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(userEmailInput.getBytes(StandardCharsets.UTF_8));
                String sha256hex = DatatypeConverter.printHexBinary(hash);

                // 更新評估上下文，將加密後的電子郵件設置回上下文
                EvaluationContext updatedContext = ctx.getEvaluationContext().toBuilder()
                        .attribute("userEmailInput", sha256hex)
                        .build();
                return Optional.of(updatedContext);
            } catch (Exception e) {
                // 處理加密錯誤
                System.err.println("Error hashing userEmailInput: " + e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public void after(HookContext<Boolean> ctx, FlagEvaluationDetails<Boolean> details, Map<String, Object> hints) {
        // 成功評估後執行的代碼
        System.out.println("Flag evaluation was successful.");
    }

    @Override
    public void error(HookContext<Boolean> ctx, Exception error, Map<String, Object> hints) {
        // 評估過程中出現錯誤時執行的代碼
        System.err.println("Error during flag evaluation: " + error.getMessage());
    }

    @Override
    public void finallyAfter(HookContext<Boolean> ctx, Map<String, Object> hints) {
        // 無論成功或失敗，最終都會執行的代碼
        System.out.println("Final cleanup after flag evaluation.");
    }
}

