package com.mirco.springboot.di.app.springboot_di.services;

import java.util.List;
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Qualifier; @Qualifier("productList")
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import com.mirco.springboot.di.app.springboot_di.models.Product;
import com.mirco.springboot.di.app.springboot_di.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    
    private ProductRepository repository ;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }


    @Value("${config.taxRate}")
    private Double taxRate;
    
    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(p -> {

            
            Double priceTax = p.getPrice() * taxRate;
            Product newProduct = (Product)p.clone();
            newProduct.setPrice(priceTax.longValue());
            // p.setPrice(priceTax.longValue());
            // return p;
            return newProduct;
        }).collect(Collectors.toList());
    }



    @Override
    public Product findById(Long id) {
        return repository.findById(id);
    }



}

