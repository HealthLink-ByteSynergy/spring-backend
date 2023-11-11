package com.example.demo.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User_Entity;
import com.example.demo.exception.User_DuplicateEmailException;
import com.example.demo.exception.User_NotFoundException;
import com.example.demo.exception.User_WrongPasswordException;
import com.example.demo.repository.User_Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class User_ServiceImpl implements User_Service {

    private final User_Repository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Jwt_Service jwtService;

    @Override
    public String signup(User_Entity user) throws User_DuplicateEmailException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String userId = UUID_Service.getUUID();
        User_Entity userEntity = User_Entity
                .builder()
                .id(userId)
                .username(user.getUser())
                .email(user.getEmail())
                .password(encodedPassword)
                .gender(user.getGender())
                .role(Role.USER)
                .build();
        try{
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new User_DuplicateEmailException("Email already exists");
        }
        return jwtService.generateToken(userEntity);
    }

    @Override
    public String login(User_Entity user) throws User_NotFoundException, User_WrongPasswordException {
        System.out.println("Authenticating user: " + user.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
        }catch(AuthenticationException e) {
            throw new User_WrongPasswordException("Wrong password");
        }

        System.out.println("Authentication successful: " + user.getEmail());
        User_Entity userEntity = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new User_NotFoundException("User not found"));
        return jwtService.generateToken(userEntity);
    }
}
