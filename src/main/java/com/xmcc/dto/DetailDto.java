package com.xmcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author 邓桥
 * @date 2019-04-18 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDto{
    @JsonProperty("openid")
    private String buyerOpenid;
    private String orderId;
}
