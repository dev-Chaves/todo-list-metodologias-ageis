package com.devchaves.toDoList.services;

import com.devchaves.toDoList.dtos.LoginDTO;
import com.devchaves.toDoList.dtos.LoginResponse;
import com.devchaves.toDoList.dtos.UserDTO;
import com.devchaves.toDoList.entitys.UsersEntity;
import com.devchaves.toDoList.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public Map<String, Object> registerUser(UserDTO userDTO){

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
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

    public LoginResponse authenticateUser(LoginDTO loginDTO){

        Optional<UsersEntity> userOpt = userRepository.findByEmail(loginDTO.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), userOpt.get().getPassword())){
            throw new BadCredentialsException("Senha ou Email inválido");
        }

        var now = Instant.now();
        var expiresIn = 864000L;

        var clains = JwtClaimsSet.builder()
                .issuer("myBackend")
                .subject(userOpt.get().getUserId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(clains)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
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
