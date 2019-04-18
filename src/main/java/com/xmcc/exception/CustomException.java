package com.xmcc.exception;

import com.xmcc.common.ResultEnums;

/**
 * @author 邓桥
 * @date 2019-04-17 14:31
 */
public class CustomException extends RuntimeException{
    private int  code;

    public CustomException(int code,String message){
        super(message);
        this.code = code;
    }
    public CustomException(String message){
        this(ResultEnums.FAIL.getCode(),message);
    }
}
