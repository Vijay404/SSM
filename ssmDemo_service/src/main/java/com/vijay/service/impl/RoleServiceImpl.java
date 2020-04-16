package com.vijay.service.impl;

import com.github.pagehelper.PageHelper;
import com.vijay.dao.RoleDao;
import com.vijay.domain.Permission;
import com.vijay.domain.Role;
import com.vijay.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page, size);
        return roleDao.findAll();
    }

    @Override
    public int save(Role role) throws Exception {
        return roleDao.save(role);
    }

    @Override
    public List<Permission> findRoleByIdAndAllPermission(String roleId) throws Exception {
        return roleDao.findRoleByIdAndAllPermission(roleId);
    }

    @Override
    public int addPermissionToRole(String roleId, String[] permissionIds) throws Exception {
        int result = 0;
        for(String permissionId:permissionIds) {
            result = roleDao.addPermissionToRole(roleId,permissionId);
        }
        return result;
    }

    @Override
    public Role findById(String roleId) throws Exception{
        return roleDao.findById(roleId);
    }
}
