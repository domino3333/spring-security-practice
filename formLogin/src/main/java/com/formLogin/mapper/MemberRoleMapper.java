package com.formLogin.mapper;


import com.formLogin.domain.Member;
import com.formLogin.domain.MemberRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRoleMapper {


    List<MemberRole> readMemberRoleById(String id);
}
