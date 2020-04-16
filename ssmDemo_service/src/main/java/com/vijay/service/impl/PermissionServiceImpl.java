package com.vijay.service.impl;

import com.github.pagehelper.PageHelper;
import com.vijay.dao.PermissionDao;
import com.vijay.domain.Permission;
import com.vijay.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findAll(int page, int size) throws Exception {
        PageHelper.startPage(page, size);
        return permissionDao.findAll();
    }

    @Override
    public Permission findById(String permissionId) throws Exception {
        return permissionDao.findById(permissionId);
    }

    @Override
    public int save(Permission permission) throws Exception {
        return permissionDao.save(permission);
    }
}
