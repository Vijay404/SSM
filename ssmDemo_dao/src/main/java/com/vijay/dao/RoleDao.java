package com.vijay.dao;

import com.vijay.domain.Permission;
import com.vijay.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    //根据用户id查询所有的角色信息
    @Select("select * from role where id in(select roleId from users_role where userId = #{userId})")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.vijay.dao.PermissionDao.findPermissionByRoleId"))
    })
    List<Role> findByUserId(String userId);

    @Select("select * from role")
    List<Role> findAll() throws Exception;

    //保存角色
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    int save(Role role) throws Exception;

    //根据权限id查找角色信息
    @Select("select * from role where id in(select roleId from role_permission where permissionId = #{id})")
    List<Role> findRolesByPermissionId() throws Exception;

    //查询出角色没有的权限信息
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId = #{roleId}) ")
    List<Permission> findRoleByIdAndAllPermission(String roleId);

    //保存角色权限信息
    @Insert("insert into role_permission(permissionId,roleId) values(#{permissionId},#{roleId})")
    int addPermissionToRole(@Param("roleId") String roleId,@Param("permissionId") String permissionId) throws Exception;

    //根据id查询角色
    @Select("select * from role where id = #{roleId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.vijay.dao.PermissionDao.findPermissionsByRoleId")),
            @Result(property = "users",column = "id",javaType = java.util.List.class,many = @Many(select = "com.vijay.dao.UserDao.findUsersByRoleId"))
    })
    Role findById(String roleId) throws Exception;
}
