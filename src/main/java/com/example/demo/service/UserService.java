package com.example.demo.service;

import com.example.demo.entity.CustomEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWrongPasswordException;

public interface UserService {
    String signup(UserEntity user) throws UserDuplicateEmailException, InvalidFormatException;
    String doctorSignup(CustomEntity customuser) throws UserDuplicateEmailException, InvalidFormatException;
    UserEntity getUserDetails(String token) throws UserNotFoundException;
    String login(UserEntity user) throws UserNotFoundException, UserWrongPasswordException;
}
 