package com.vijay.service;

import com.vijay.domain.Product;

import java.util.List;

/**
 * 业务层
 */
public interface ProductService {
    //查询所有
    List<Product> findAll(int page,int size) throws Exception;
    //保存新产品
    int save(Product product);
}
