package com.formLogin.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    public String mainPage(HttpSession session, Authentication authentication){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("main에서의 name:{}",name);
        session.setAttribute("main용","무엇이있을까요");
        log.info("main용 이라는 세션에 들어있는 값:{}",session.getAttribute("main용"));
        log.info("main에서의 authentication.getPricipal:{}",authentication.getPrincipal());
        log.info("what is this:{}",session.getAttribute("SPRING_SECURITY_CONTEXT"));



        return "main";
    }
}
