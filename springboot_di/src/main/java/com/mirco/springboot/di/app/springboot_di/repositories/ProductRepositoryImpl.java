package com.mirco.springboot.di.app.springboot_di.repositories;

import com.mirco.springboot.di.app.springboot_di.models.Product;
import java.util.List;

import org.springframework.stereotype.Repository;
// import org.springframework.web.context.annotation.RequestScope;
// import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
// @RequestScopeScope

@Repository("productList")
public class ProductRepositoryImpl implements ProductRepository {

    private List<Product> data;

    public ProductRepositoryImpl() {
        this.data=Arrays.asList(
            new Product(1L, "Memoria Corsair" , 300L),
            new Product(2L,"Cpu intel core i9", 850L),
            new Product(3L, "Teclado razer mini 60%", 180L),
            new Product(4L, "Motherboard gigabyte", 490L)
        );
    }
    
    @Override
    public List<Product> findAll() {
        return data;
    }

    @Override
    public Product findById(Long id) {
        return data.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }
}
