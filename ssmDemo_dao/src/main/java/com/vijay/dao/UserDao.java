package com.vijay.dao;

import com.vijay.domain.Role;
import com.vijay.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    //根据用户名查找用户，同时查出用户对应的权限信息
    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.vijay.dao.RoleDao.findByUserId"))
    })
    UserInfo findByUsername(String Username) throws Exception;

    //查询所有用户信息
    @Select("select * from users")
    List<UserInfo> findAll() throws Exception;

    //保存单个用户信息
    @Insert("insert into users(username,password,phoneNum,email,status) values(#{username},#{password},#{phoneNum},#{email},#{status})")
    int save(UserInfo userInfo) throws Exception;

    //根据用户id查出数据库中用户详细信息
    @Select("select * from users where id = #{userId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.vijay.dao.RoleDao.findByUserId"))
    })
    UserInfo findById(String userId) throws Exception;

    @Select("select * from role where id not in(select roleId from users_role where userId = #{userId})")
    List<Role> findUserByIdAndAllRole(String userId) throws Exception;

    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    int addRoleToUser(@Param("userId")String userId,@Param("roleId")String roleId) throws Exception;

    //根据角色id查询出所有的用户信息
    @Select("select * from users where id in(select userId from users_role where roleId = #{roleId})")
    List<UserInfo> findUsersByRoleId(String roleId);
}
