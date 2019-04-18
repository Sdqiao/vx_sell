package com.xmcc.dto;

import com.xmcc.entity.OrderDetail;
import com.xmcc.entity.OrderMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 邓桥
 * @date 2019-04-17 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto implements Serializable{
    @JsonProperty("openId")
    private String buyerOpenid;
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    private Date createTime;
    private Date updateTime;
    private List<OrderDetail> orderDetailList;

    public static OrderListDto adapt(OrderMaster orderMaster){
        OrderListDto orderListDto = new OrderListDto();
        BeanUtils.copyProperties(orderMaster,orderListDto);
        return orderListDto;
    }
}
