package org.example.myapp.service.impl;

import org.example.myapp.model.User;
import org.example.myapp.repository.UserRepository;
import org.example.myapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getRandomUser() {
        List<User> users = userRepository.findAll();
        Random random = new Random();
        if (!users.isEmpty()) {
            return users.get(random.nextInt(users.size()));
        }
        return null;
    }
}
