package com.xmcc.test;


import com.xmcc.entity.ProductCategory;
import com.xmcc.repository.ProductCategoryRespository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 邓桥
 * @date 2019-04-15 14:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
    @Autowired
    private ProductCategoryRespository productCategoryRespository;
    @Test
    public void test1(){
        List<ProductCategory> all = productCategoryRespository.findAll();
        System.out.println(all.size());
    }
}
