package com.mirco.curso.springboot.webapp.springboot_webapp.controllers;



import java.util.List;
import java.util.Arrays;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.thymeleaf.expression.Arrays;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mirco.curso.springboot.webapp.springboot_webapp.models.User;






@Controller
public class UserController {

    @GetMapping("/details") 
public String details(Model model){

    User user = new User("mirco",           "santoni");

    model.addAttribute("title", "Hola Mundo");
    model.addAttribute("user", user);

    return "details";
}

    @GetMapping("/list")    
    public String list(ModelMap model) {



       // model.addAttribute("users", users);
        model.addAttribute("title", "estoy muy confundido man");
        return "list";   
    }

    @ModelAttribute("users")
    public List<User> usersModel() {
        return Arrays.asList(   
            new User("fer", "cepeda", "correoespectacular"),
            new User("manu", "martinez"),
            new User("bruno", "olaiz"),
            new User("milanesa", "asesina"));
    }
}
