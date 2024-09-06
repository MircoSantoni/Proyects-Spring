package com.mirco.curso.springboot.webapp.springboot_webapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mirco.curso.springboot.webapp.springboot_webapp.models.dto.ParamDto;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mirco.curso.springboot.webapp.springboot_webapp.models.User;



@RestController
@RequestMapping("/api/var")
public class PathVariableController {

    @Value("${config.code}")
    private Integer code;
    @Value("${config.username}")
    private String username;
    @Value("${config.message}")
    private String message;
    @Value("${config.listOfValues}")
    private List<String> listOfValues;

  // @Value("#{${config.valuesMap}}")
  // private Map<String, Object> valuesMap;

    @Autowired
    private Environment environment;

    @GetMapping("/baz/{message}")
    public ParamDto baz(@PathVariable String message) {

        ParamDto param = new ParamDto();
        param.setMesagge(message);
        return param;
    }

    @GetMapping("/mix/{product}/{id}")
    public Map<String, Object> mixPathVar(@PathVariable String product, @PathVariable Long id) {
        
        Map<String, Object > json = new HashMap<>();
        json.put("product", product);
        json.put("id", id);
        return json;
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        
        user.setName(user.getName().toUpperCase());
        user.setLastname(user.getLastname().toUpperCase());
        return user;
    }


    @GetMapping("/values")
    public Map<String ,Object> values(@Value("${config.message}") String message) {

        Map<String, Object> values = new HashMap<>();
        values.put("username", username);
        values.put("message", message);
        values.put("message2", environment.getProperty("config.message"));
        
        values.put("code",code);
        values.put("code2",environment.getProperty("config.code",Long.class));

        values.put("listOfValues",listOfValues);
        //values.put("valueMap",valuesMap);
           
        return values;
    }
    
}