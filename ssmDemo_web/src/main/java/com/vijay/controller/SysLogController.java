package com.vijay.controller;

import com.github.pagehelper.PageInfo;
import com.vijay.domain.SysLog;
import com.vijay.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "5")Integer size) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<SysLog> sysLogs = sysLogService.findAll(page,size);
        //分页对象完成分页信息的封装
        PageInfo pageInfo = new PageInfo(sysLogs);
        mv.addObject("pageInfo_log",pageInfo);
        mv.setViewName("syslog-list");
        return mv;
    }

}
