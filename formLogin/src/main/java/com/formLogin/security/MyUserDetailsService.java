package com.formLogin.security;


import com.formLogin.domain.Member;
import com.formLogin.domain.MemberRole;
import com.formLogin.mapper.MemberMapper;
import com.formLogin.mapper.MemberRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        Member member = null;
        List<MemberRole> memberRoleList = new ArrayList<>();
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        try {
            member = memberMapper.readMemberById(id);
            memberRoleList = memberRoleMapper.readMemberRoleById(id);

            for(MemberRole item : memberRoleList){
                simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_"+item.getRoleName()));
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


        return MyUser.builder()
                .member(member)
                .name(member.getName())
                .id(member.getId())
                .roles(simpleGrantedAuthorityList)
                .password(member.getPassword()).build();

    }
}
