package com.example.openfeaturedemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/page")
public class PageController {
    @GetMapping("/post-goods")
    public String postGoods(Model model) {
        return "post-goods"; // 返回模板文件的名稱（不包括後綴）
    }

    @GetMapping("/get-goods")
    public String getGoods(Model model) {
        return "get-goods"; // 返回模板文件的名稱（不包括後綴）
    }
}
