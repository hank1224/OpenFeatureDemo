package com.example.openfeaturedemo.hooks;

import com.example.openfeaturedemo.exception.BadRequestException;
import dev.openfeature.sdk.*;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class BeforeHookEmailCryptoHook implements Hook<Boolean> {
    private static final Logger logger = Logger.getLogger(BeforeHookEmailCryptoHook.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BeforeHookEmailCryptoHook.class);

    @Override
    public Optional<EvaluationContext> before(HookContext<Boolean> ctx, Map<String, Object> hints) {
        String userEmailSubmit = ctx.getCtx().getValue("userEmailSubmit").asString();
        Boolean shouldBeforeHookFailed = ctx.getCtx().getValue("shouldBeforeHookFailed").asBoolean();
        Boolean shouldAfterHookFailed = ctx.getCtx().getValue("shouldAfterHookFailed").asBoolean();

        if (userEmailSubmit == null) {
            throw new BadRequestException("userEmailSubmit is null.");
        }

        if (shouldBeforeHookFailed) {
            logger.severe("[Before Hook] Before hook failed.");
            throw new RuntimeException("Before Hook failed, shouldBeforeHookFailed is true.");
        }

        try {
            String sha256hex = shouldAfterHookFailedTrigger(hashEmail(userEmailSubmit), shouldAfterHookFailed);  // shouldAfterHookFailed Trigger

            Map<String, Value> values = new HashMap<>();
            values.put("userEmailSubmit", new Value(sha256hex));
            EvaluationContext updatedContext = new ImmutableContext(ctx.getCtx().getTargetingKey(), values);
            return Optional.of(updatedContext);
        } catch (NoSuchAlgorithmException e) {
            logger.severe("SHA-256 hashing algorithm not available: " + e.getMessage());
            throw new IllegalStateException("Required hashing algorithm not available", e);
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
            logger.info("Making invalid hash, shouldFlagEvalFailed is true.");
            return sha256hex + "error";
        }
        return sha256hex;
    }

    @Override
    public void after(HookContext<Boolean> ctx, FlagEvaluationDetails<Boolean> details, Map<String, Object> hints) {
        String sha256hex = ctx.getCtx().getValue("userEmailSubmit").asString();
        logger.info("[After Hook] Got Hashed userEmailSubmit from edited evalCtx: " + sha256hex);
        if (sha256hex.length() != 64) {
            logger.severe("[After Hook] After Hook Failed, Invalid hash length: " + sha256hex.length());
            throw new RuntimeException("[After Hook] Invalid hash value: " + sha256hex);
        }
        logger.info("[After Hook] Flag evaluation was successful.");
    }

    @Override
    public void error(HookContext<Boolean> ctx, Exception error, Map<String, Object> hints) {
        logger.severe("[Error Hook] Error during flag evaluation: " + error.getMessage());
    }

    @Override
    public void finallyAfter(HookContext<Boolean> ctx, Map<String, Object> hints) {
        logger.info("[Finally Hook] Logged after flag evaluation.");
    }
}
