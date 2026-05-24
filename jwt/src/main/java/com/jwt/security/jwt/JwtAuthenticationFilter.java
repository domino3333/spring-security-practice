package com.jwt.security.jwt;

import com.jwt.security.MyUser;
import com.jwt.security.MyUserDetailsService;
import com.jwt.security.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 하나의 필터로서 서블릿 필터 -> security filter chain 안에 있는
    // UsernamePasswordAuthenticationFilter 앞에 꽂아넣을 핕터임

    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);

            if(jwtTokenProvider.validateToken(token)){
                String id = jwtTokenProvider.getId(token);
                MyUser myUser = (MyUser) myUserDetailsService.loadUserByUsername(id);

                //로그인 요청 때는 인증 token을 직접 만들어서
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(myUser,null,myUser.getAuthorities());
                //security context에 꽂아 넣기
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
