package com.mirco.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirco.curso.springboot.app.springboot_crud.entities.Product;
import com.mirco.curso.springboot.app.springboot_crud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByID(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product){

        Optional<Product> optionalProduct = repository.findById(id);
        
        if (optionalProduct.isPresent()){

            Product prod = optionalProduct.orElseThrow();
            prod.setName(product.getName());
            prod.setDescription(product.getDescription());
            prod.setPrice(product.getPrice());
            prod.setSku(product.getSku());
            
            return Optional.of(repository.save(prod));
        }
        return optionalProduct;
    }
    
    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        optionalProduct.ifPresent(prod -> {
            repository.delete(prod);
        });
        return optionalProduct;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        
        return repository.existsBySku(sku); 
    }

}
