package com.jwt.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id,password);
        Authentication authentication = authenticationManager.authenticate(token);


        return ResponseEntity.ok("로그인 완료");
    }
}