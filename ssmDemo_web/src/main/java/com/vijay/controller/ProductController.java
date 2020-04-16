package com.vijay.controller;

import com.github.pagehelper.PageInfo;
import com.vijay.domain.Product;
import com.vijay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 产品控制器
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/findAll")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,
                                @RequestParam(name = "size",required = true,defaultValue = "4")Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productService.findAll(page,size);
        PageInfo<Product> pageInfo_pro = new PageInfo<>(products);
        mv.addObject("pageInfo_pro",pageInfo_pro);
        mv.setViewName("product-list");
        return mv;
    }

    @RequestMapping("/save")
    public String save(Product product, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int result = productService.save(product);
        if(result == 0){
            return "failure";
        }
        else{
            response.sendRedirect(request.getContextPath()+"/product/findAll");
            return null;
        }
    }
}
