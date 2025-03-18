package com.mirco.springcloud.msvc.products.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mirco.springcloud.msvc.products.MsvcProductsApplication;
import com.mirco.springcloud.msvc.products.entitites.Product;
import com.mirco.springcloud.msvc.products.repositories.ProductRepository;
import com.mirco.springcloud.msvc.products.services.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final MsvcProductsApplication msvcProductsApplication;

    private final ProductRepository productRepository;
    private final Environment environment;




    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() { 
        return ((List<Product>) productRepository.findAll()).stream().map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true) 
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        });
    }

}
