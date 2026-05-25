package com.smartcart.user_service.controller;

import com.smartcart.user_service.entity.User;
import com.smartcart.user_service.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {

        return userService.loginUser(
                user.getUsername(),
                user.getPassword()
        );
    }

    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }
}