package com.formLogin.security;


import com.formLogin.domain.Member;
import com.formLogin.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        try {
            Member member = memberMapper.readMemberById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
