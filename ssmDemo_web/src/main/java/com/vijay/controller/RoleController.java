package com.vijay.controller;

import com.github.pagehelper.PageInfo;
import com.vijay.domain.Permission;
import com.vijay.domain.Role;
import com.vijay.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Role> roleList = roleService.findAll(page,size);
        //分页封装
        PageInfo pageInfo_role = new PageInfo(roleList);
        mv.addObject("pageInfo_role",pageInfo_role);
        mv.setViewName("role-list");
        return mv;
    }

    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam("id")String roleId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Role role = roleService.findById(roleId);
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    @RequestMapping("/save")
    public String save(Role role) throws Exception{
        int result = roleService.save(role);
        if(result == 0){
            return "failure";
        }else {
            return "redirect:findAll";
        }
    }

    @RequestMapping("/findRoleByIdAndAllPermission")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(value = "id",required = true) String roleId) throws Exception{
        ModelAndView mv= new ModelAndView();
        //根据id查出角色信息
        Role role = roleService.findById(roleId);
        mv.addObject("role",role);
        List<Permission> permissionList = roleService.findRoleByIdAndAllPermission(roleId);
        mv.addObject("permissionList",permissionList);
        mv.setViewName("role-permission-add");
        return mv;
    }

    //保存角色的权限信息
    @RequestMapping("/addPermissionToRole")
    public String addPermissionToRole(@RequestParam(name = "roleId",required = true) String roleId,@RequestParam(name = "ids",required = true) String[] permissionIds) throws Exception{
        int result = roleService.addPermissionToRole(roleId,permissionIds);
        if(result == 0){
            return "failure";
        }else{
            return "redirect:findAll";
        }
    }
}
