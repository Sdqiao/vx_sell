package com.xmcc.entity;

import com.xmcc.common.OrderEnums;
import com.xmcc.common.PayEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 邓桥
 * @date 2019-04-17 11:33
 */
@Data
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_master")
@Builder
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderEnums.NEW.getCode();
    private Integer payStatus = PayEnums.WAIT.getCode();
    private Date createTime;
    private Date updateTime;
}
