package com.vijay.service.impl;

import com.github.pagehelper.PageHelper;
import com.vijay.dao.ProductDao;
import com.vijay.domain.Product;
import com.vijay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productService")
@Transactional //注解开启事务
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return productDao.findAll();
    }

    @Override
    public int save(Product product) {
        return productDao.save(product);
    }
}
