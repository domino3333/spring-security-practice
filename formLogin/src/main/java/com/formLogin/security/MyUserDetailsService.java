package com.formLogin.security;


import com.formLogin.domain.Member;
import com.formLogin.domain.MemberRole;
import com.formLogin.mapper.MemberMapper;
import com.formLogin.mapper.MemberRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private final MemberRoleMapper memberRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Member member = memberMapper.readMemberById(id);
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        if(member == null){
            //던지면 authenticationProvider가 받게 됨
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        List<MemberRole> memberRoleList = memberRoleMapper.readMemberRoleById(id);

        for (MemberRole item : memberRoleList) {
            //저장할 땐 ROLE_를 붙여서 UserDetails 구현체로 넣기
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + item.getRoleName()));

        }
        return MyUser.builder()
                .member(member)
                .name(member.getName())
                .id(member.getId())
                .roles(simpleGrantedAuthorityList)
                .password(member.getPassword()).build();
    }
}