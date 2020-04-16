package com.vijay.controller;

import com.github.pagehelper.PageInfo;
import com.vijay.domain.Orders;
import com.vijay.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    //查询所有订单信息，未分页
//    @RequestMapping("/findAll")
//    public ModelAndView findAll() throws Exception {
//        ModelAndView mv = new ModelAndView();
//        List<Orders> orders = ordersService.findAll();
//        mv.addObject("ordersList",orders);
//        mv.setViewName("orders-list");
//        return mv;
//    }
    //查询所有订单信息，分页后
    @RequestMapping("/findAll")
    @Secured("ROLE_USER")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> orders = ordersService.findAll(page,size);
        //PageInfo就是一个分页Bean，自动完成分页信息的封装
        PageInfo pageInfo_orders = new PageInfo(orders);

        mv.addObject("pageInfo_orders",pageInfo_orders);
        mv.setViewName("orders-page-list");
        return mv;
    }

    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam(name = "id",required = true) String ordersId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Orders orders = ordersService.findById(ordersId);
        System.out.println(orders.getTravellers());
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }
}
