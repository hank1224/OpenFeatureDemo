package com.example.openfeaturedemo.dto;

public class LogDataDTO {
    private String message;
    private String flagKey;
    private String userEmailHashed;
    private Object hookContext;
    private Object EvaluationDetail;

    public LogDataDTO() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFlagKey() {
        return flagKey;
    }

    public void setFlagKey(String flagKey) {
        this.flagKey = flagKey;
    }

    public String getUserEmailHashed() {
        return userEmailHashed;
    }

    public void setUserEmailHashed(String userEmailHashed) {
        this.userEmailHashed = userEmailHashed;
    }

    public Object getHookContext() {
        return hookContext;
    }

    public void setHookContext(Object hookContext) {
        this.hookContext = hookContext;
    }

    public Object getEvaluationDetail() {
        return EvaluationDetail;
    }

    public void setEvaluationDetail(Object evaluationDetail) {
        EvaluationDetail = evaluationDetail;
    }

    @Override
    public String toString() {
        return "LogDataDTO{" +
                "message='" + message + '\'' +
                ", flagKey='" + flagKey + '\'' +
                ", userEmailHashed=" + userEmailHashed + '\'' +
                ", hookContext=" + hookContext + '\'' +
                ", EvaluationDetail=" + EvaluationDetail +
                '}';
    }
}
