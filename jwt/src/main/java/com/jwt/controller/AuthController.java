package com.jwt.controller;


import com.jwt.dto.LoginDto;
import com.jwt.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getId(),dto.getPassword());

        //인증 성공 시 authentication으로 돌려받고 이걸로 토큰을 만드는 것
        //provider가 이 토큰에서 id를 뽑아 userDetailsService를 호출
        Authentication authentication = authenticationManager.authenticate(token);
        String jwt = jwtTokenProvider.createToken(authentication);



        return ResponseEntity.ok(jwt);
    }
}