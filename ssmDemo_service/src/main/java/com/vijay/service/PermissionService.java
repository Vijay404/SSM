package com.vijay.service;

import com.vijay.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll(int page,int size) throws Exception;

    //根据id查询权限详情信息
    Permission findById(String permissionId) throws Exception;

    //保存权限信息
    int save(Permission permission) throws Exception;
}
