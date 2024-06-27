package com.example.openfeaturedemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeforeHookEmailCryptoRequestDTO {
    @JsonProperty("userEmailInput")
    private String userEmailInput;

    @JsonProperty("evalOutcome")
    private String evalOutcome;

    public BeforeHookEmailCryptoRequestDTO() {
    }

    public BeforeHookEmailCryptoRequestDTO(String userEmailInput, String evalOutcome) {
        this.userEmailInput = userEmailInput;
        this.evalOutcome = evalOutcome;
    }

    // Getter and Setter

    public String getUserEmailInput() {
        return userEmailInput;
    }

    public void setUserEmailInput(String userEmailInput) {
        this.userEmailInput = userEmailInput;
    }

    public String getEvalOutcome() {
        return evalOutcome;
    }

    public void setEvalOutcome(String evalOutcome) {
        this.evalOutcome = evalOutcome;
    }
}
