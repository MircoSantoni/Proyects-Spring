package com.mirco.springcloud.msvc.products.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirco.springcloud.msvc.products.entitites.Product;
import com.mirco.springcloud.msvc.products.repositories.ProductRepository;
import com.mirco.springcloud.msvc.products.services.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() { 
        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true) 
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

}
