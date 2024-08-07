package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.dto.*;
import com.example.openfeaturedemo.service.PageService;
import dev.openfeature.sdk.FlagEvaluationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/page")
public class PageController {
    private final PageService pageService;
    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/post-goods")
    public String postGoods() {
        return "post-goods"; // 返回模板文件的名稱（不包括後綴）
    }

    @GetMapping("/get-goods")
    public String getGoods() {
        return "get-goods";
    }

    @GetMapping("/secret-button")
    public String secretButton(Model model) {
        SecretButtonDTO secretButtonDetail = pageService.getSecretButtonFlag();
        model.addAttribute("secretButtonDetail", secretButtonDetail);
        return "secret-button";
    }

    @GetMapping("/multi-button")
    public String multiButton(Model model) {
        MultiButtonDTO multiButtonDTO = pageService.getMultiButtonFlag();
        model.addAttribute("multiButtonDTO", multiButtonDTO);
        return "multi-button";
    }

    @GetMapping("/openfeature-client-side")
    public String openfeatureClientSide(Model model) {
        FeatbitClientConfDTO featbitClientConfDTO = pageService.getFeatbitClientConf();
        model.addAttribute("featbitClientConfDTO", featbitClientConfDTO);
        return "openfeature-client-side";
    }

    @GetMapping("/before-hook-email-crypto")
    public String beforeHookEmailCrypto(Model model) {
        FeatbitClientConfDTO featbitClientConfDTO = pageService.getFeatbitClientConf();
        model.addAttribute("featbitClientConfDTO", featbitClientConfDTO);
        return "before-hook-email-crypto";
    }

    @PostMapping("/before-hook-email-crypto/server-eval")
    public ModelAndView beforeHookEmailCryptoServerEval(@ModelAttribute ServerEvalRequestDTO serverEvalRequestRequest) {
        ModelAndView modelAndView = new ModelAndView("before-hook-email-crypto-server-eval");
        FlagEvaluationDetails<Boolean> flagEvaluationDetails = pageService.beforeHookEmailCrypto(serverEvalRequestRequest);
        modelAndView.addObject("FlagEvaluationDetails", flagEvaluationDetails);
        return modelAndView;
    }
}
