package com.andres.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.andres.springcloud.msvc.items.models.Item;
import com.andres.springcloud.msvc.items.models.Product;
import com.andres.springcloud.msvc.items.services.ItemService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ItemController {

    private final Logger logger =  LoggerFactory.getLogger(ItemController.class);
    private final ItemService service;
    private final CircuitBreakerFactory cBreakerFactory;

    //@Qualifier("itemServiceWebClient") <- esto en el constructor le dice al servicio lo que queremos que inyecte
    public ItemController( ItemService service,
                            CircuitBreakerFactory ccBreakerFactory) {
        this.service = service;
        this.cBreakerFactory = ccBreakerFactory;
    }

    @GetMapping
    public List<Item> list(@RequestParam(required = false) String name,
                            @RequestHeader(name="token-request", required=false) String token) {
                            System.out.println(name + token);
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Item> itemOptional = cBreakerFactory.create("items").run(() -> service.findById(id), e -> {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("Milanesa");
            product.setPrice(500.00);
            return Optional.of(new Item(product, 5));
        });
        if(itemOptional.isPresent()){
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(404)
                .body(Collections.singletonMap(
                    "message",
                "No existe el producto en el microservicio msvc-products"));
    }
    

                    // IMPLEMENTACION ORIGINAL SIN CIRCUIT BRAKER
    // @GetMapping("/{id}")
    // public ResponseEntity<?> details(@PathVariable Long id) {
    //     Optional<Item> itemOptional = service.findById(id);
    //     if(itemOptional.isPresent()){
    //         return ResponseEntity.ok(itemOptional.get());
    //     }
    //     return ResponseEntity.status(404)
    //             .body(Collections.singletonMap(
    //                 "message",
    //             "No existe el producto en el microservicio msvc-products"));
    // }
}
