package com.devchaves.toDoList.services;

import com.devchaves.toDoList.entitys.UsersEntity;
import com.devchaves.toDoList.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
