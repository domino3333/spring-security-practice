package com.formLogin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TestController {

    @GetMapping("/login")
    public String test() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("test에서의 name:{}",name);
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("main에서의 name:{}",name);


        return "main";
    }
}
