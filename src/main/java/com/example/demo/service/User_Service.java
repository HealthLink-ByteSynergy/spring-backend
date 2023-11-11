package com.example.demo.service;

import com.example.demo.entity.User_Entity;
import com.example.demo.exception.User_DuplicateEmailException;
import com.example.demo.exception.User_NotFoundException;
import com.example.demo.exception.User_WrongPasswordException;

public interface User_Service {
    String signup(User_Entity user) throws User_DuplicateEmailException;

    String login(User_Entity user) throws User_NotFoundException, User_WrongPasswordException;
}
 