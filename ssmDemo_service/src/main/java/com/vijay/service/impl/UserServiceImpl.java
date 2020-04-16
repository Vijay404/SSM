package com.vijay.service.impl;

import com.github.pagehelper.PageHelper;
import com.vijay.dao.UserDao;
import com.vijay.domain.Role;
import com.vijay.domain.UserInfo;
import com.vijay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthorities(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus() != 0,true,true,true,getAuthorities(userInfo.getRoles()));
        return user;
    }

    public List<SimpleGrantedAuthority> getAuthorities(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role:roles){
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }


    @Override
    public List<UserInfo> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

    @Override
    public int save(UserInfo userInfo) throws Exception{
        //对存入的密码进行加密处理
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        return userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String userId) throws Exception {
        return userDao.findById(userId);
    }

    @Override
    public List<Role> findUserByIdAndAllRole(String userId) throws Exception {
        return userDao.findUserByIdAndAllRole(userId);
    }

    @Override
    public int addRoleToUser(String userId, String[] ids) throws Exception {
        int result = 0;
        for (String roleId:ids) {
            result += userDao.addRoleToUser(userId,roleId);
        }
        return result;
    }
}
