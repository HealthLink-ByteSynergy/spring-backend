package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWrongPasswordException;
import com.example.demo.entity.ErrorMessage;

@ControllerAdvice
@CrossOrigin(origins = "*")
public class ExceptionController {
    @ExceptionHandler(UserDuplicateEmailException.class)
    public ResponseEntity<ErrorMessage> handleUser_DuplicateEmailException(UserDuplicateEmailException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUser_NotFoundException(UserNotFoundException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(UserWrongPasswordException.class)
    public ResponseEntity<ErrorMessage> handleUser_WrongPasswordException(UserWrongPasswordException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleItemNotFoundException(ItemNotFoundException exception){
        ErrorMessage message=new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(InvalidFormatException exception){
        ErrorMessage message=new ErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
