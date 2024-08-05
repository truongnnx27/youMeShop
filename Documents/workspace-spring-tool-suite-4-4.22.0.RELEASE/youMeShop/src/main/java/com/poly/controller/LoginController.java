package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/formLogin")
    public String view() {
        return "page/dangnhap";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "page/homePage";
    }
}
