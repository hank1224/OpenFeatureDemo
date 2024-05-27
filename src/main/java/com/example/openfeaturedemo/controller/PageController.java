package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.service.DynamicFeatureFlagService;
import com.example.openfeaturedemo.service.PageService;
import dev.openfeature.sdk.FlagEvaluationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/page")
public class PageController {
    private final PageService pageService;
    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @Autowired
    private DynamicFeatureFlagService featureFlagService;

    @GetMapping("/feature-flag")
    public String getFeatureFlag(@RequestParam String flagKey,
                                 @RequestParam String defaultValue,
                                 @RequestParam String provider) {
        return featureFlagService.evaluateFeatureFlag(flagKey, defaultValue, provider);
    }

    @GetMapping("/feature-flag-details")
    public FlagEvaluationDetails<String> getFeatureFlagDetails(@RequestParam String flagKey,
                                                               @RequestParam String defaultValue,
                                                               @RequestParam String provider) {
        return featureFlagService.evaluateFeatureFlagDetails(flagKey, defaultValue, provider);
    }

    @GetMapping("/post-goods")
    public String postGoods(Model model) {
        return "post-goods"; // 返回模板文件的名稱（不包括後綴）
    }

    @GetMapping("/get-goods")
    public String getGoods(Model model) {
        return "get-goods";
    }

    @GetMapping("/secret-button")
    public String secretButton(Model model) {
        String secretButtonStatus = pageService.secretButton();
        model.addAttribute("secret_button_feature", "secret-button_enabled".equals(secretButtonStatus));
        return "secret-button";
    }

    @GetMapping("/secret-button2")
    public String secretButton2(Model model) {
        String secretButtonStatus = pageService.secretButton2();
        model.addAttribute("secret_button_feature", "secret-button_enabled".equals(secretButtonStatus));
        return "secret-button";
    }
}
