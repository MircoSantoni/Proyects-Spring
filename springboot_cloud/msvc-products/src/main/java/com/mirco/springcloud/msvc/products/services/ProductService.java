package com.mirco.springcloud.msvc.products.services;

import java.util.*;

import com.mirco.springcloud.msvc.products.entitites.Product;

public interface ProductService {
    
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}
