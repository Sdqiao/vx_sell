package com.xmcc.service.Impl;

import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.entity.ProductCategory;
import com.xmcc.repository.ProductCategoryRespository;
import com.xmcc.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邓桥
 * @date 2019-04-16 19:28
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRespository productCategoryRespository;
    @Override
    public ResultResponse<List<ProductCategoryDto>> findAll(){
        List<ProductCategory> all = productCategoryRespository.findAll();
        //利用流转换成dto集合
        return ResultResponse.success(all.stream().map(productCategory -> ProductCategoryDto.adapt(productCategory)).collect(Collectors.toList()));
    }
}
