package com.xmcc.common;

import lombok.Getter;

@Getter
public enum ResultEnums {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    PRODUCT_UP(0,"正常"),
    PRODUCT_DOWN(1,"商品下架"),
    PARAM_ERROR(2,"参数异常"),
    NOT_EXITS(3,"该商品不存在"),
    ORDER_NOTEXIST(0,"该订单不存在");

    private int code;
    private String msg;

    ResultEnums(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
