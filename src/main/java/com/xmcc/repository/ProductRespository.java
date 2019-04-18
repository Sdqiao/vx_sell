package com.xmcc.repository;

import com.xmcc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface ProductRespository extends JpaRepository<Product,String> {

    List<Product> findByProductStatusAndCategoryTypeIn(Integer status,List<Integer> categoryList);
}
