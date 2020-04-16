package com.vijay.controller;

import com.github.pagehelper.PageInfo;
import com.vijay.domain.Role;
import com.vijay.domain.UserInfo;
import com.vijay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView findAll(
            @RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
            @RequestParam(name = "size",required = true,defaultValue = "5")Integer size)
            throws Exception{
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findAll(page,size);
        //分页对象完成分页封装
        PageInfo pageInfo_user = new PageInfo(userList);
        mv.addObject("pageInfo_user",pageInfo_user);
        mv.setViewName("user-list");
        return mv;
    }

    @RequestMapping("/save")
    @PreAuthorize("authentication.principal.username == 'ssm'")
    public String save(UserInfo userInfo) throws Exception{
        int result = userService.save(userInfo);
        if(result == 0){
            return "failure";
        }
        else{
            return "redirect:findAll";
        }
    }

    @RequestMapping("/findById")
    @RolesAllowed({"USER","ADMIN"})
    public ModelAndView findById(@RequestParam("id")String userId) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo user = userService.findById(userId);
        mv.addObject("user",user);
        mv.setViewName("user-show");
        return mv;
    }

    @RequestMapping("/findUserByIdAndAllRole")
    @RolesAllowed({"ADMIN"})
    public ModelAndView findUserByIdAndAllRole(@RequestParam("id")String userId)throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Role> roles = userService.findUserByIdAndAllRole(userId);
        UserInfo user = userService.findById(userId);
        mv.addObject("user",user);
        mv.addObject("roleList",roles);
        mv.setViewName("user-role-add");
        return mv;
    }

    @RequestMapping("/addRoleToUser")
    @RolesAllowed({"ADMIN"})
    public String addRoleToUser(@RequestParam(value = "userId",required = true)String userId,@RequestParam(value = "ids",required = true)String[] ids)throws Exception{
        ModelAndView mv = new ModelAndView();
        int result = userService.addRoleToUser(userId, ids);
        if(result == 0){
            return "failure";
        }else {
            return "redirect:findAll";
        }
    }
}
