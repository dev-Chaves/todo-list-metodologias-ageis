package com.devchaves.toDoList.services;

import com.devchaves.toDoList.config.JwtConfig;
import com.devchaves.toDoList.dtos.UserDTO;
import com.devchaves.toDoList.entitys.UsersEntity;
import com.devchaves.toDoList.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    public Map<String, Object> registerUser(UserDTO userDTO){

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email já está em uso!");
        }

        UsersEntity user = new UsersEntity();

        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        UsersEntity savedUser = userRepository.save(user);

        Map<String, Object> response = new HashMap<>();

        response.put("userId", savedUser.getUserId());

        return response;

    }


}
