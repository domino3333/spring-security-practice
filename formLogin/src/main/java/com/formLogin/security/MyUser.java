package com.formLogin.security;


import com.formLogin.domain.Member;
import com.formLogin.domain.MemberRole;
import lombok.Builder;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class MyUser implements UserDetails {

    private Member member;
    private Long memberNo;
    private String id;
    private String password;
    private String name;
    private List<? extends GrantedAuthority> roles;

    public MyUser(Member member,Long memberNo, String id, String password, String name) {
        this.member = member;
        this.memberNo = memberNo;
        this.id = id;
        this.password = password;
        this.name = name;
    }


    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
