package com.xmcc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 邓桥
 * @date 2019-04-17 14:14
 */
@Data
@ApiModel("订单项参数实体类")
@DynamicUpdate
public class OrderDetailDto implements Serializable {
    @NotBlank(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id",dataType = "String")//swagger 参数的描述信息
    private String productId;
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1,message = "数量不能少于一件")
    @ApiModelProperty(value = "商品数量",dataType = "Integer",example = "1")
    private Integer productQuantity;
}
