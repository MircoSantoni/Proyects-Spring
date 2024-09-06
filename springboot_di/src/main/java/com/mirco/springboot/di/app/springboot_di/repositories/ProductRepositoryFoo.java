package com.mirco.springboot.di.app.springboot_di.repositories;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mirco.springboot.di.app.springboot_di.models.Product;


@Repository("productFoo")
public class ProductRepositoryFoo implements ProductRepository{

    @Override
    public List<Product> findAll() {

        return Collections.singletonList(new Product(1L,"Monitor de la reconcha de la lora",1500L));
    }

    @Override
    public Product findById(Long id) {

        return new Product(1L,"Monitor de la reconcha de la lora",1500L);    
    }
    

}
