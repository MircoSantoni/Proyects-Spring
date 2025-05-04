package com.mirco.springcloud.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mirco.springcloud.msvc.users.entities.*;
import com.mirco.springcloud.msvc.users.repositories.RoleRepository;
import com.mirco.springcloud.msvc.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(getRoles(user));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {

        Optional<User> optionalUser = this.findById(id);
        return optionalUser.map(userDb -> {

            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            if (user.isEnabled() == null) {
                userDb.setEnabled(true);
            } else {
                userDb.setEnabled(user.isEnabled());
            }
            userDb.setRoles(getRoles(user));

            return Optional.of(userRepository.save(userDb));

        }).orElseGet(() -> null);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        optionalRole.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
            adminRoleOptional.ifPresent(roles::add);
        }
            return roles;
    }

}