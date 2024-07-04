package com.example.openfeaturedemo.dto;

import dev.openfeature.sdk.EvaluationContext;

public class ServerEvalRequestDTO {
    private String userEmailSubmit;
    private boolean shouldFlagEvalFailed;
    private boolean shouldBeforeHookFailed;
    private boolean shouldAfterHookFailed;

    // Getters and Setters
    public String getUserEmailSubmit() {
        return userEmailSubmit;
    }

    public void setUserEmailSubmit(String userEmailSubmit) {
        this.userEmailSubmit = userEmailSubmit;
    }

    public boolean isShouldFlagEvalFailed() {
        return shouldFlagEvalFailed;
    }

    public void setShouldFlagEvalFailed(boolean shouldFlagEvalFailed) {
        this.shouldFlagEvalFailed = shouldFlagEvalFailed;
    }

    public boolean isShouldBeforeHookFailed() {
        return shouldBeforeHookFailed;
    }

    public void setShouldBeforeHookFailed(boolean shouldBeforeHookFailed) {
        this.shouldBeforeHookFailed = shouldBeforeHookFailed;
    }

    public boolean isShouldAfterHookFailed() {
        return shouldAfterHookFailed;
    }

    public void setShouldAfterHookFailed(boolean shouldAfterHookFailed) {
        this.shouldAfterHookFailed = shouldAfterHookFailed;
    }
}
