package com.khodataev.my.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RootController {

    @RequestMapping(value = "/")
    public String getHomePage() {
        return "redirect:login";

    }

    @RequestMapping(value = "hello")
    public String getHelloPage(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("This is Root Page");
        model.addAttribute("messages", messages);
        return "static/helloPage";
    }

//    @GetMapping(value = "login")
//    public String getLoginPage() {
//        return "static/loginPage";
//    }

    @GetMapping(value = "vip")
    public String getVipPage(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("This is VIP Page");
        model.addAttribute("messages", messages);
        return "static/vipPage";
    }
}
