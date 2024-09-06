package com.mirco.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import com.mirco.curso.springboot.app.springboot_crud.entities.Product;

public interface ProductService {

    List<Product> findAll();
    
    Optional<Product> findByID (Long id);

    Product save (Product product);

    Optional<Product> update (Long id, Product product);

    Optional<Product> delete (Long id);
    
    boolean existsBySku(String sku);
} 