package com.mirco.curso.springboot.app.springboot_crud.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.mirco.curso.springboot.app.springboot_crud.entities.User;

@Service
public interface UserService {

    Set<User> findAll();

    User save(User user);

    boolean existByUsername(String username);
    
} 