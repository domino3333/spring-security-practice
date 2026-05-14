package com.formLogin.mapper;


import com.formLogin.domain.Member;
import com.formLogin.domain.MemberRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRoleMapper {


    MemberRole readMemberRoleById(String id) throws Exception;
}
