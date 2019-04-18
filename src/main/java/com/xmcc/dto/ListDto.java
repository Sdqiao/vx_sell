package com.xmcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * @author 邓桥
 * @date 2019-04-18 09:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListDto implements Serializable {
    @JsonProperty("openId")
    private String buyerOpenid;
    private Integer page;
    private Integer size;
}
