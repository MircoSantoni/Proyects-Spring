package com.mirco.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;

import com.mirco.springcloud.msvc.items.entities.Item;

public interface ItemService {

    List<Item> findAll();
    Optional<Item> findById(Long id);

}