package com.vijay.dao;

import com.vijay.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface MemberDao {
    //根据会员id查询会员信息
    @Select("select * from member where id = #{memberId}")
    Member findById(String memberId) throws Exception;
}
