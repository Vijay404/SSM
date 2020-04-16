package com.vijay.dao;

import com.vijay.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao {

    //查询所有产品信息
    @Select("select * from product")
    List<Product> findAll() throws Exception;

    //保存一个产品信息
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productStatus,productDesc) " +
            " values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productStatus},#{productDesc})")
    int save(Product product);

    //根据id查找一个产品信息
    @Select("select * from product where id = #{id}")
    Product findById(String id);
}
