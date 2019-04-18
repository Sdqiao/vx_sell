package com.xmcc.service;

import com.xmcc.common.ResultResponse;
import com.xmcc.entity.Product;

public interface ProductService {
    ResultResponse querryList();
    //根据商品的id查询商品信息
    ResultResponse<Product> findById(String productId);
    //商品修改
    void update(Product product);
}
