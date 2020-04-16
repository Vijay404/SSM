package com.vijay.service;

import com.vijay.domain.Orders;

import java.util.List;

/**
 * 订单业务层
 */
public interface OrdersService {
    //查询所有订单列表
    List<Orders> findAll(int page,int size) throws Exception;

    Orders findById(String ordersId) throws Exception;
}
