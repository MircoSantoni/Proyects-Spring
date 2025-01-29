package com.mirco.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mirco.curso.springboot.app.springboot_crud.entities.User;
import com.mirco.curso.springboot.app.springboot_crud.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")  // Define el endpoint base como /api/users
public class UserController {

    @Autowired
    private UserService service;  // Inyecta el servicio de usuarios

    // Método para obtener una lista de todos los usuarios
    @GetMapping
    public Set<User> list(){
        return service.findAll();
    }

    // Método para crear un nuevo usuario validado por @Valid
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        // Si hay errores de validación, devuelve los errores en la respuesta
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        // Si no hay errores, crea el usuario y devuelve un estado 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){
        user.setAdmin(false);
        return create(user, result);
    }

    // Método auxiliar para manejar los errores de validación y devolver un mapa con los mensajes
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);  // Devuelve un 400 (BAD REQUEST) si hay errores
    }
}
