package com.devchaves.toDoList.controllers;

import com.devchaves.toDoList.dtos.LoginDTO;
import com.devchaves.toDoList.dtos.UserDTO;
import com.devchaves.toDoList.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController

public class AuthControllers {

    private final UserService userService;

    public AuthControllers(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok().body(userService.authenticateUser(loginDTO));
    }
}
