package com.mirco.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mirco.springcloud.msvc.products.entitites.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

    
} 