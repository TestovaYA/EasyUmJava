package org.example.myapp.service;

import org.example.myapp.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long id);
    User getRandomUser();
}
