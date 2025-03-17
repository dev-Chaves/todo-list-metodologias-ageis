package com.devchaves.toDoList.services;

import com.devchaves.toDoList.config.JwtConfig;
import com.devchaves.toDoList.dtos.LoginDTO;
import com.devchaves.toDoList.dtos.UserDTO;
import com.devchaves.toDoList.entitys.UsersEntity;
import com.devchaves.toDoList.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

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

    public Map<String, String> authenticateUser(LoginDTO loginDTO){

        Optional<UsersEntity> userOpt = userRepository.findByEmail(loginDTO.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), userOpt.get().getPassword())){
            throw new RuntimeException("Senha ou Email inválido");
        }

        String token = jwtConfig.generateToken(loginDTO.getEmail());

        Map<String, String> response = new HashMap<>();

        response.put("token", token);

        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsersEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado."));

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public UsersEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email " + email));
    }
}
