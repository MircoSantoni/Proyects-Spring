package com.mirco.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mirco.springcloud.msvc.items.entities.Item;

@Service
public interface ItemService {

    List<Item> findAll();
    Optional<Item> findById(Long id);

}