package com.mirco.curso.springboot.app.springboot_crud.services;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirco.curso.springboot.app.springboot_crud.entities.Role;
import com.mirco.curso.springboot.app.springboot_crud.entities.User;
import com.mirco.curso.springboot.app.springboot_crud.repositories.RoleRepository;
import com.mirco.curso.springboot.app.springboot_crud.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Set<User> findAll() {
        List<User> userList = (List<User>) repository.findAll();
        return new HashSet<>(userList);
    }
    

    @Override
    @Transactional
    public User save(User user) {
        
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        
        optionalRoleUser.ifPresent(roles::add);

        if(user.isAdmin()){

            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");

            optionalRoleAdmin.ifPresent(roles::add);
        }
        user.setRoles(roles);
    
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
