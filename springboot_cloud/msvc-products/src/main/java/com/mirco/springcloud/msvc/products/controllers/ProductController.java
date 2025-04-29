package com.mirco.springcloud.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mirco.springcloud.msvc.products.entitites.Product;
import com.mirco.springcloud.msvc.products.services.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
public class ProductController {
    final private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(this.productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> listOne(@PathVariable Long id) throws InterruptedException {
        if (id.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado");
        }
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L);
        }
        Optional<Product> optionalProduct = productService.findById(id);
        return ResponseEntity.ok(optionalProduct);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {

        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            Product productDb = optionalProduct.orElseThrow();

            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setCreatedAt(product.getCreatedAt());

            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            this.productService.deleteById(id);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }

}