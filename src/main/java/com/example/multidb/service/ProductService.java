package com.example.multidb.service;

import com.example.multidb.entity.secondary.Product;
import com.example.multidb.mapper.secondary.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "secondaryTransactionManager")
public class ProductService {

    private final ProductMapper productMapper;

    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }

    public Product getProductById(Long id) {
        return productMapper.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productMapper.findByCategory(category);
    }

    public Product createProduct(Product product) {
        productMapper.insert(product);
        return product;
    }

    public Product updateProduct(Product product) {
        productMapper.update(product);
        return product;
    }

    public void deleteProduct(Long id) {
        productMapper.deleteById(id);
    }
}