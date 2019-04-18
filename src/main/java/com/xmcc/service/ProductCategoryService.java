package com.xmcc.service;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邓桥
 * @date 2019-04-16 16:52
 */
@Service
public interface ProductCategoryService {
    ResultResponse<List<ProductCategoryDto>> findAll();
}
