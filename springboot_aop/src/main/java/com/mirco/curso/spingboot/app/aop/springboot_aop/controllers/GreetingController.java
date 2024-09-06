package com.mirco.curso.spingboot.app.aop.springboot_aop.controllers;

import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.mirco.curso.spingboot.app.aop.springboot_aop.services.GreetingServiceImpl;

@RestController
public class GreetingController {

    @Autowired
    private GreetingServiceImpl greetingService;

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {

        return ResponseEntity.ok(Collections.singletonMap("greetings", greetingService.sayHello("pepe","como estas ")));

    }
    
    @GetMapping("/greetingError")
    public ResponseEntity<?> greetingError() {

        return ResponseEntity.ok(Collections.singletonMap("greetings", greetingService.sayHelloError("pepe","como estas ")));

    }
}
