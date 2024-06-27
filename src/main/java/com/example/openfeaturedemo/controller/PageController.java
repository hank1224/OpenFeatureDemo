package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.dto.FeatbitClientConfDTO;
import com.example.openfeaturedemo.dto.MultiButtonDTO;
import com.example.openfeaturedemo.dto.SecretButtonDTO;
import com.example.openfeaturedemo.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
