package com.xmcc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 邓桥
 * @date 2019-04-17 11:27
 */
@Data
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
    @Id
    private String detailId;
    private String orderId;
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productIcon;
}
