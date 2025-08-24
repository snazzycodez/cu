package com.example.multidb.mapper.secondary;

import com.example.multidb.entity.secondary.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    
    List<Product> findAll();
    
    Product findById(@Param("id") Long id);
    
    List<Product> findByCategory(@Param("category") String category);
    
    int insert(Product product);
    
    int update(Product product);
    
    int deleteById(@Param("id") Long id);
}