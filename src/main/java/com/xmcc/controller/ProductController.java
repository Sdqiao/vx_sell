package com.xmcc.controller;

import com.xmcc.common.ResultResponse;
import com.xmcc.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 邓桥
 * @date 2019-04-16 16:47
 */
@RestController
@RequestMapping("buyer/product")
@Api(description = "商品信息接口")
public class ProductController{
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    @ApiOperation(value = "商品信息列表")
    public ResultResponse list(){
        return productService.querryList();
    }
}
