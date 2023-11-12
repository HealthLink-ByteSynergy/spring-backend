package com.example.demo.exception;

public class UserDuplicateEmailException extends CustomExcpetion{
    public UserDuplicateEmailException(String message) {
        super(message);
    }
}
