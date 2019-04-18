package com.xmcc.repository;

import com.xmcc.entity.ProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRespository extends JpaRepository<ProductCategory,Integer> {
}
