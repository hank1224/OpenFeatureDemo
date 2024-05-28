package com.example.openfeaturedemo.dto;

public class MultiButtonDTO {

    private boolean flagsmithButtonIsEnabled;

    private boolean featbitButtonIsEnabled;

    public MultiButtonDTO(boolean flagsmithButtonIsEnabled, boolean featbitButtonIsEnabled) {
        this.flagsmithButtonIsEnabled = flagsmithButtonIsEnabled;
        this.featbitButtonIsEnabled = featbitButtonIsEnabled;
    }

    public boolean isFlagsmithButtonIsEnabled() {
        return flagsmithButtonIsEnabled;
    }

    public void setFlagsmithButtonIsEnabled(boolean flagsmithButtonIsEnabled) {
        this.flagsmithButtonIsEnabled = flagsmithButtonIsEnabled;
    }

    public boolean isFeatbitButtonIsEnabled() {
        return featbitButtonIsEnabled;
    }

    public void setFeatbitButtonIsEnabled(boolean featbitButtonIsEnabled) {
        this.featbitButtonIsEnabled = featbitButtonIsEnabled;
    }
}
