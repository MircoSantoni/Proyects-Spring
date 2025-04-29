package com.mirco.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mirco.springcloud.msvc.products.entitites.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

    
} 