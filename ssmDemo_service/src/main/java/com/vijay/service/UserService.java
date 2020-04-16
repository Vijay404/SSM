package com.vijay.service;

import com.vijay.domain.Role;
import com.vijay.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    //查询所有用户
    List<UserInfo> findAll(int page,int size) throws Exception;

    //保存用户信息
    int save(UserInfo userInfo) throws Exception;

    //根据 用户id查询用户详细信息
    UserInfo findById(String userId) throws Exception;

    //根据用户id查询出用户所用的角色信息
    List<Role> findUserByIdAndAllRole(String userId) throws Exception;

    //存储用户的角色信息
    int addRoleToUser(String userId, String[] ids) throws Exception;
}
