package com.mirco.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mirco.springcloud.msvc.items.clients.ProductFeignClient;
import com.mirco.springcloud.msvc.items.entities.Item;
import com.mirco.springcloud.msvc.items.entities.Product;

public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductFeignClient feignClient;

    @Override
    public List<Item> findAll() {
        return feignClient.findAll().stream().map(product -> {
            Random random = new Random();
            return new Item(product, random.nextInt(10) + 1);

        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Product product = feignClient.details(id);
        if ( product == null) {
            return Optional.empty();
        }

        return Optional.ofNullable( new Item(feignClient.details(id), new Random().nextInt(10) +1));
    }

}
