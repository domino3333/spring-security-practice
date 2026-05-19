package com.formLogin2.security;


import com.formLogin2.domain.Member;
import com.formLogin2.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    //private final MemberMapper memberMapper;
    //private final MemberRoleMapper memberRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        //id를 가지고 db에서 Member 정보를 가져오기
        Member member = memberMapper.readMemberById(id);
        //join으로 권한 가져오기
        List<MemberRole> list = memberRoleMapper.readMemberRole(id);
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (MemberRole role : list) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }


        return MyUserDetail.builder()
                .memberNo(member.getMemberNo())
                .id(member.getId())
                .password(member.getPassword())
                .name(member.getName())
                .memberRoleList(authorities)
                .build();
    }
}
