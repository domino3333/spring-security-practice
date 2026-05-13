package com.formLogin.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        return http
                .formLogin(fr -> fr
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                )
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/login","/loginProc").permitAll()
                )
                .csrf(csrf->csrf.disable())
                .build();

    }
}
