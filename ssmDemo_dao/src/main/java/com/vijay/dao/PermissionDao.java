package com.vijay.dao;

import com.vijay.domain.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionDao {

    @Select("select * from permission where id in(select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> findPermissionByRoleId(String roleId) throws Exception;

    //查询所有权限的信息
    @Select("select * from permission")
    List<Permission> findAll() throws Exception;

    @Select("select * from permission where id = #{permissionId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "permissionName",column = "permissionName"),
            @Result(property = "url",column = "url"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.vijay.dao.RoleDao.findRolesByPermissionId"))

    })
    Permission findById(String permissionId) throws Exception;

    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    int save(Permission permission) throws Exception;

    @Select("select * from permission where id in(select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> findPermissionsByRoleId(String roleId);
}
