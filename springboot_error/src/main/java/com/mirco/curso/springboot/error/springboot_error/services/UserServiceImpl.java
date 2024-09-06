package com.mirco.curso.springboot.error.springboot_error.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mirco.curso.springboot.error.springboot_error.models.domain.User;

@Service
public class UserServiceImpl implements UserService{

    private List<User> users;

public UserServiceImpl() {
    this.users = new ArrayList<>();
    users.add(new User( "Pepe", "Argento", 1l ));
    users.add(new User( "Moni", "Argento", 2l));
    users.add(new User( "Fatiga", "El perro", 3l));
    users.add(new User( "Marialena", "Fuseneco", 4l));
}

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        for (User u : users) {
            if(u.getId().equals(id)){
                user= u;
                break;
            }
        }
        return  user;    
    }

}
