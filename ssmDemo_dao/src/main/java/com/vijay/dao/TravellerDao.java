package com.vijay.dao;

import com.vijay.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TravellerDao {

    //根据订单id查询出的游客id来查询出游客的信息
    @Select("select * from traveller where id in (select travellerId from order_traveller where orderId = #{ordersId})")
    List<Traveller> findByOrdersId(String ordersId) throws Exception;

}
