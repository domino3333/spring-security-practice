package com.formLogin2.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/form")
    public ResponseEntity<?> dfsa(){

        return ResponseEntity.ok("fff");
    }
}
