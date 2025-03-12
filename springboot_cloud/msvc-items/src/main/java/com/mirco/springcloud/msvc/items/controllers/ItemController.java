package com.mirco.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mirco.springcloud.msvc.items.entities.Item;
import com.mirco.springcloud.msvc.items.services.ItemService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ItemController {

    @Autowired
    private  ItemService itemService;

    @GetMapping
    public List<Item> list() {
        return itemService.findAll();
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Item> details(@PathVariable Long id) {
        Optional<Item> optionalItem = itemService.findById(id);
        if (optionalItem.isPresent()) {
            return ResponseEntity.ok(optionalItem.get());
        }
        return ResponseEntity.notFound().build();
    }

}
