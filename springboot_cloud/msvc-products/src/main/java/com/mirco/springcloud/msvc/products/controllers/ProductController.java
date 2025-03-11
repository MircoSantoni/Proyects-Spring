package com.mirco.springcloud.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mirco.springcloud.msvc.products.entitites.Product;
import com.mirco.springcloud.msvc.products.services.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
public class ProductController {
    final private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(this.productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> listOne(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        return ResponseEntity.ok(optionalProduct);
    }
 
} 