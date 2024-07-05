package com.example.openfeaturedemo.hooks;

import com.example.openfeaturedemo.exception.BadRequestException;
import dev.openfeature.sdk.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

public class BeforeHookEmailCryptoHook implements Hook<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(BeforeHookEmailCryptoHook.class);

    @Override
    public Optional<EvaluationContext> before(HookContext<Boolean> ctx, Map<String, Object> hints) {
        try {
            String userEmailSubmit = ctx.getCtx().getValue("userEmailSubmit").asString();
            if (userEmailSubmit == null) {
                logger.error("[Before Hook] userEmailSubmit is null.");
                throw new BadRequestException("userEmailSubmit is null.");
            }

            Boolean shouldBeforeHookFailed = ctx.getCtx().getValue("shouldBeforeHookFailed").asBoolean();
            if (shouldBeforeHookFailed) {
                logger.warn("[Before Hook] Before hook is set to fail, shouldBeforeHookFailed is true.");
                throw new RuntimeException("Before Hook failed, shouldBeforeHookFailed is true.");
            }

            String sha256hex = hashEmail(userEmailSubmit);
            MutableContext beforeHookEditContext = (MutableContext) ctx.getCtx();
            beforeHookEditContext.setTargetingKey("Case5-HashedEmail: " + sha256hex);

            Boolean shouldAfterHookFailed = ctx.getCtx().getValue("shouldAfterHookFailed").asBoolean();
            // 僅製作 Ctx 內的 Hash 錯誤，不影響 TargetingKey 的同一使用者判定。
            beforeHookEditContext.add("userEmailSubmit", shouldAfterHookFailedTrigger(sha256hex, shouldAfterHookFailed));
            logger.debug("[Before Hook] Hashed email and updated context successfully.");

            return Optional.of(beforeHookEditContext);
        } catch (NoSuchAlgorithmException e) {
            logger.error("[Before Hook] SHA-256 hashing algorithm not available.", e);
            throw new IllegalStateException("Required hashing algorithm not available", e);
        } catch (Exception e) {
            logger.error("[Before Hook] Unexpected error occurred.", e);
            throw e;
        }
    }

    private String hashEmail(String userEmail) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(userEmail.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // shouldAfterHookFailed: 讓Hash值長度異常，將會在After Hook檢查出錯誤
    private String shouldAfterHookFailedTrigger(String sha256hex, Boolean shouldAfterHookFailed) {
        if (shouldAfterHookFailed) {
            logger.info("[Before Hook] Making invalid hash, shouldFlagEvalFailed is true.");
            return sha256hex + "(error)";
        }
        return sha256hex;
    }

    @Override
    public void after(HookContext<Boolean> ctx, FlagEvaluationDetails<Boolean> details, Map<String, Object> hints) {
        String sha256hex = ctx.getCtx().getValue("userEmailSubmit").asString();
        logger.info("[After Hook] Got Hash from beforeHookEditContext: " + sha256hex);

        if (sha256hex.length() != 64) {
            logger.error("[After Hook] After Hook Failed, Invalid hash length: " + sha256hex.length());
            throw new RuntimeException("[After Hook] Invalid hash value: " + sha256hex);
        }
        logger.info("[After Hook] Hash valid checked, this is a valid hash.");
    }

    @Override
    public void error(HookContext<Boolean> ctx, Exception error, Map<String, Object> hints) {
        logger.error("[Error Hook] Error during flag evaluation: {}", error.getMessage(), error);
        logger.error("[Error Hook] Current ctx: {}", ctx.getCtx().asMap().toString());
    }

    @Override
    public void finallyAfter(HookContext<Boolean> ctx, Map<String, Object> hints) {
        logger.info("[Finally Hook] Completed flag evaluation.");
    }
}