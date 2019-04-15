package com.xmcc.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 邓桥
 * @date 2019-04-15 14:10
 */
@RestController
public class Test1 {
    @GetMapping("/test")
    public String test(){
        return "hello java";
    }
}
