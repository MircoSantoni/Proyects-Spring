package com.mirco.curso.springboot.error.springboot_error.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mirco.curso.springboot.error.springboot_error.models.domain.User;
import com.mirco.curso.springboot.error.springboot_error.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/App")
public class AppController {

    @Autowired
    private UserService service;

    @GetMapping("/")    
    public String index() {
        //int value = 100/ 0;
        int value = Integer.parseInt("10x");

        System.out.println(value);
        return "Ok 200";
    }

    @GetMapping("/Show/{id}")
    public User show(@PathVariable(name = "id") Long id) {

        return service.findById(id);
    }
}
