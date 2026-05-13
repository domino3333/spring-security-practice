package com.base64.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DefaultSecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {


        return http.build();

    }
}