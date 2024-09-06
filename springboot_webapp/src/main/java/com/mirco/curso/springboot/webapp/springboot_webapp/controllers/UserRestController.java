package com.mirco.curso.springboot.webapp.springboot_webapp.controllers;


import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mirco.curso.springboot.webapp.springboot_webapp.models.User;
import com.mirco.curso.springboot.webapp.springboot_webapp.models.dto.UserDto;



@RequestMapping("/api")
@RestController
public class UserRestController {

    @GetMapping("/details")
public UserDto details(){  
    UserDto userDto = new UserDto();

    User user = new User("mirco","santoni");

    userDto.setTitle("Hola Mundo");
    userDto.setName(user.getName());
    userDto.setLastname(user.getLastname());

    return userDto;
}   

    @GetMapping("/list")

    public List <User> list() {
        User user = new User("mirco","santoni");
        User user2 = new User("luke","nacif");
        User user3 = new User("mathew","castle");


        List<User> users = Arrays.asList(user, user2, user3);
        // List<User> users = new ArrayList<>();
        // users.add(user);
        // users.add(user2);
        // users.add(user3);
        
        return users;
    }
}

//@GetMapping("/details")
//public Map<String, Object> details(){
    //User user = new User("mirco","santoni");
    //Map <String , Object> body = new HashMap<>();

    //body.put("title", "Hola Mundo");
    //body.put("user", user);


   // return body;
//}
//}

