package com.mirco.curso.springboot.webapp.springboot_webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
    @GetMapping({"","/","/home"})
    public String home() {
        return "forward:/list";
    }
    
}
