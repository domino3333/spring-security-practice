//package com.jwt.security;
//
//import com.jwt.domain.Member;
//import com.jwt.domain.MemberRole;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//
//    @Override
//    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
//
//        Member member = memberMapper.readMemberById(id);
//        List<MemberRole> roleList = memberRoleMapper.readMemberRoleById(id);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        for (MemberRole role : roleList) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
//        }
//
//        return MyUser.builder()
//                .name(member.getName())
//                .no(member.getNo())
//                .password(member.getPassword())
//                .id(member.getId())
//                .authorities(authorities)
//                .build();
//    }
//}
