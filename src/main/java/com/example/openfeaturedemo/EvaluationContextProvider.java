package com.example.openfeaturedemo;

import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.ImmutableContext;
import dev.openfeature.sdk.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EvaluationContextProvider {

    public EvaluationContext createContext() {
        return new ImmutableContext("user-key", new HashMap<String, Value>() {{
            put("tester-id", new Value("tester"));
        }});
    }
}
