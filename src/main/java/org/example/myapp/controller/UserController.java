package org.example.myapp.controller;

import org.example.myapp.model.User;
import org.example.myapp.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("randomUser", userServiceImpl.getRandomUser());
        return "index";
    }

    @RequestMapping("/getUser")
    public String getUser(@RequestParam Long id, Model model) {
        Optional<User> userOpt = userServiceImpl.findUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            model.addAttribute("message", "Пользователя с таким id не существует.");
        }
        model.addAttribute("randomUser", userServiceImpl.getRandomUser());
        return "index";
    }
}

