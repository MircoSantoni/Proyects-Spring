package com.mirco.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;   
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mirco.curso.springboot.app.springboot_crud.entities.Product;
import com.mirco.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // @Autowired
    // private ProductValidation validation;
    
    @GetMapping // get
    public List<Product> list(){

        return productService.findAll();
    }
    
    @GetMapping("/{id}") // get 
    public ResponseEntity<?> view( @PathVariable Long id){ //path variable es para declarar que esperamos variables de la url
        Optional<Product> optionalProduct = productService.findByID(id);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping // post es para crear
    public ResponseEntity<?> create( @Valid @RequestBody Product product, BindingResult result) { //request body es para mapear la solicitud como un objeto java
       // validation.validate(product, result );

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product)); // binding result es para validar, debe estar a la derecha de la clase que pasamos como argumento
    }
    
    
    @PutMapping("/{id}") // put es para reemplazar
    public ResponseEntity<?> update(  @Valid  @RequestBody Product product, BindingResult result , @PathVariable Long id ) { //request body es para mapear la solicitud como un objeto java
         //   validation.validate(product, result ); //valid tiene que ir en request body
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Product> optionalProduct =  productService.update(id, product);    
        if (optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalProduct.orElseThrow());      //path variable es para declarar que esperamos variables de la url
            
        }
        return ResponseEntity.notFound().build();
    }
    
    
    @DeleteMapping("/{id}") // get 
    public ResponseEntity<?> delete( @PathVariable Long id){ //path variable es para declarar que esperamos variables de la url
        
        Optional<Product> optionalProduct = productService.delete(id);
        
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
    
}
