package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWrongPasswordException;

public interface UserService {
    String signup(UserEntity user) throws UserDuplicateEmailException;
    UserEntity getUserDetails(UserEntity user) throws UserNotFoundException;
    String login(UserEntity user) throws UserNotFoundException, UserWrongPasswordException;
}
 