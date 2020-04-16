package com.vijay.service;

import com.vijay.domain.Permission;
import com.vijay.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll(int page,int size) throws Exception;

    //保存角色信息
    int save(Role role) throws Exception;

    //查询角色以及其没有的权限信息
    List<Permission> findRoleByIdAndAllPermission(String roleId) throws Exception;

    //保存角色的权限信息
    int addPermissionToRole(String roleId, String[] permissionIds) throws Exception;

    //根据id查询角色信息
    Role findById(String roleId) throws Exception;
}
