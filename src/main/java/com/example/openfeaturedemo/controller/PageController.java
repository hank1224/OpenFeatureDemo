package com.example.openfeaturedemo.controller;

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
}
