package com.formLogin2.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .formLogin(fl -> fl
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/api/main")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/form")
                        .authenticated()
                ).csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .build();
    }

    @Bean
    PasswordEncoder providePasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
