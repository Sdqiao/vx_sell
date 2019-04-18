package com.xmcc.service.Impl;

import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductDto;
import com.xmcc.entity.Product;
import com.xmcc.repository.ProductRespository;
import com.xmcc.service.ProductCategoryService;
import com.xmcc.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 邓桥
 * @date 2019-04-16 19:21
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductRespository productRespository;
    @Override
    public ResultResponse querryList(){
        ResultResponse<List<ProductCategoryDto>> categoryServiceResult = productCategoryService.findAll();
        List<ProductCategoryDto> categorydtoList = categoryServiceResult.getData();
        if(CollectionUtils.isEmpty(categorydtoList)){
            return categoryServiceResult;//如果分类列表为空 直接返回了
        }
        //获得类目编号集合
        List<Integer> categoryTypeList = categorydtoList.stream().map(categorydto -> categorydto.getCategoryType()).collect(Collectors.toList());
        //查询商品列表  这里商品上下架可以用枚举 方便扩展
        List<Product> productInfoList = productRespository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), categoryTypeList);
        //多线程遍历 取出每个商品类目编号对应的 商品列表 设置进入类目中
        List<ProductCategoryDto> finalResultList = categorydtoList.parallelStream().map(categorydto -> {
            categorydto.setProductDtoList(productInfoList.stream().
                    filter(productInfo -> productInfo.getCategoryType() == categorydto.getCategoryType()).map(productInfo ->
                    ProductDto.adapt(productInfo)).collect(Collectors.toList()));
            return categorydto;
        }).collect(Collectors.toList());
        return ResultResponse.success(finalResultList);
    }

    @Override
    public ResultResponse<Product> findById(String productId){
        //先判断id是否为空
        if(StringUtils.isBlank(productId)){
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg()+":"+productId);
        }
        Optional<Product> byId = productRespository.findById(productId);
        if(!byId.isPresent()){
            return ResultResponse.fail(ResultEnums.NOT_EXITS.getMsg()+":"+productId);
        }
        Product product = byId.get();
        //判断商品是否下架了
        if(product.getProductStatus()==ResultEnums.PRODUCT_DOWN.getCode()){
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getMsg());
        }
        return ResultResponse.success(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
        productRespository.save(product);
    }
}
