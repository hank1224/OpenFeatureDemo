package com.example.openfeaturedemo.service;

import co.featbit.openfeature.FBProvider;
import com.example.openfeaturedemo.EvaluationContextProvider;
import dev.openfeature.contrib.providers.flagsmith.FlagsmithProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.FlagEvaluationDetails;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicFeatureFlagService {

    private final EvaluationContextProvider contextProvider;
    private final FBProvider featbitProvider;
    private final FlagsmithProvider flagsmithProvider;

    @Autowired
    public DynamicFeatureFlagService(FBProvider featbitProvider,
                                     FlagsmithProvider flagsmithProvider,
                                     EvaluationContextProvider contextProvider) {
        this.featbitProvider = featbitProvider;
        this.flagsmithProvider = flagsmithProvider;
        this.contextProvider = contextProvider;
    }

    private Client getClient(String providerName) {
        if ("featbit".equalsIgnoreCase(providerName)) {
            OpenFeatureAPI.getInstance().setProvider(featbitProvider);
        } else if ("flagsmith".equalsIgnoreCase(providerName)) {
            OpenFeatureAPI.getInstance().setProvider(flagsmithProvider);
        }
        else {
            throw new IllegalArgumentException("Unknown provider: " + providerName);
        }
        return OpenFeatureAPI.getInstance().getClient();
    }

    public String evaluateFeatureFlag(String providerName, String flagKey, String defaultValue) {
        Client client = getClient(providerName);
        EvaluationContext evalCtx = contextProvider.createContext();
        return client.getStringValue(flagKey, defaultValue, evalCtx);
    }

    public FlagEvaluationDetails<String> evaluateFeatureFlagDetails(String providerName, String flagKey, String defaultValue) {
        Client client = getClient(providerName);
        EvaluationContext evalCtx = contextProvider.createContext();
        return client.getStringDetails(flagKey, defaultValue, evalCtx);
    }
}
