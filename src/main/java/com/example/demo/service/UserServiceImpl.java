package com.example.demo.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWrongPasswordException;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String signup(UserEntity user) throws UserDuplicateEmailException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String userId = UUIDService.getUUID();
        UserEntity userEntity = UserEntity
                .builder()
                .id(userId)
                .username(user.getUser())
                .email(user.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();
        try{
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateEmailException("Email already exists");
        }
        return jwtService.generateToken(userEntity);
    }

    @Override
    public String login(UserEntity user) throws UserNotFoundException, UserWrongPasswordException {
        System.out.println("Authenticating user: " + user.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
        }catch(AuthenticationException e) {
            throw new UserWrongPasswordException("Wrong password");
        }

        System.out.println("Authentication successful: " + user.getEmail());
        UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return jwtService.generateToken(userEntity);
    }

    @Override
    public UserEntity getUserDetails(UserEntity user) throws UserNotFoundException {
        // need to change this
        Optional<UserEntity> newUser=userRepository.findByEmail(user.getEmail());
        if(newUser.isPresent()){
            return newUser.get();
        }
        else throw new UserNotFoundException("This User doesn't exist"); 
    }
}
