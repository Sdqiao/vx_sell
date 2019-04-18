package com.xmcc.dto;

import com.xmcc.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author 邓桥
 * @date 2019-04-16 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto implements Serializable {
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    public List<ProductDto> productDtoList;

    public static ProductCategoryDto adapt(ProductCategory productCategory){
        ProductCategoryDto pruductCategoryDto = new ProductCategoryDto();
        BeanUtils.copyProperties(productCategory,pruductCategoryDto);
        return pruductCategoryDto;
    }
}
