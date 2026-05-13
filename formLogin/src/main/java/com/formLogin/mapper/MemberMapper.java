package com.formLogin.mapper;


import com.formLogin.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {


    Member readMemberById(String id) throws Exception;
}
