package com.mirco.springcloud.msvc.users.services;

import java.util.Optional;

import com.mirco.springcloud.msvc.users.entities.User;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Iterable<User> findAll();

    User save(User user);

    Optional<User> update(User user, Long id);

    void deleteById(Long id);
}
