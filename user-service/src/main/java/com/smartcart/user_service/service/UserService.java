package com.smartcart.user_service.service;

import com.smartcart.user_service.entity.User;
import com.smartcart.user_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.smartcart.user_service.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Register User
    public User registerUser(User user) {

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Find User By Username
    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    // Login User
    public String loginUser(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Check Password
        if (passwordEncoder.matches(password, user.getPassword())) {

            // Generate JWT Token
            return jwtUtil.generateToken(username);
        }

        throw new RuntimeException("Invalid Password");
    }
}