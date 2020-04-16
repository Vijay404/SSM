package com.vijay.controller;

import com.github.pagehelper.PageInfo;
import com.vijay.domain.Permission;
import com.vijay.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Permission> permissionList = permissionService.findAll(page,size);
        //分页对象封装
        PageInfo pageInfo_per = new PageInfo(permissionList);
        mv.addObject("pageInfo_per",pageInfo_per);
        mv.setViewName("permission-list");
        return mv;
    }

    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam("id") String permissionId) throws Exception{
        Permission permission = permissionService.findById(permissionId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("permission", permission);
        mv.setViewName("permission-show");
        return mv;
    }

    @RequestMapping("/save")
    public String save(Permission permission) throws Exception {
        int result = permissionService.save(permission);
        if(result == 0){
            return "failure";
        }else {
            return "redirect:findAll";
        }
    }
}
