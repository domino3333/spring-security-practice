package com.formLogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/login")
    public String test() {
        return "login";
    }

    @GetMapping("/main")
    public String main(){

        return "main";
    }
}
