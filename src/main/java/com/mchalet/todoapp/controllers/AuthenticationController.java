package com.mchalet.todoapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mchalet.todoapp.model.UserModel;
import com.mchalet.todoapp.service.AuthenticationService;
import com.mchalet.todoapp.service.UserService;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        return this.authenticationService.authenticate(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        UserModel savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
