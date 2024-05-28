package com.example.openfeaturedemo.dto;

import dev.openfeature.sdk.ErrorCode;
import dev.openfeature.sdk.FlagEvaluationDetails;
import dev.openfeature.sdk.ImmutableMetadata;

public class SecretButtonDTO extends FlagEvaluationDetails<Boolean> {
    private boolean isEnabled;
    private String flagKey;
    private boolean value;
    private String variant;
    private String reason;
    private ErrorCode errorCode;
    private String errorMessage;
    private ImmutableMetadata flagMetadata;

    public SecretButtonDTO(boolean isEnabled,
                           String flagKey,
                           boolean value,
                           String variant,
                           String reason,
                           ErrorCode errorCode,
                           String errorMessage,
                           ImmutableMetadata flagMetadata) {
        this.isEnabled = isEnabled;
        this.flagKey = flagKey;
        this.value = value;
        this.variant = variant;
        this.reason = reason;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.flagMetadata = flagMetadata;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String getFlagKey() {
        return flagKey;
    }

    @Override
    public void setFlagKey(String flagKey) {
        this.flagKey = flagKey;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String getVariant() {
        return variant;
    }

    @Override
    public void setVariant(String variant) {
        this.variant = variant;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ImmutableMetadata getFlagMetadata() {
        return flagMetadata;
    }

    @Override
    public void setFlagMetadata(ImmutableMetadata flagMetadata) {
        this.flagMetadata = flagMetadata;
    }
}

