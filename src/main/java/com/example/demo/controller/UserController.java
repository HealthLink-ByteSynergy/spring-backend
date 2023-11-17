package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CustomEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWrongPasswordException;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody UserEntity user) throws UserDuplicateEmailException, InvalidFormatException {
        return userService.signup(user);
    }

    @PostMapping("/doctorsignup")
    public String doctorSignup(@RequestBody CustomEntity user) throws UserDuplicateEmailException, InvalidFormatException {
        return userService.doctorSignup(user);
    }

    @PostMapping("/getUserDetails")
    public UserEntity getUserDetails(@RequestBody String token) throws UserNotFoundException{
        return userService.getUserDetails(token);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity user, HttpServletResponse response) throws UserWrongPasswordException, UserNotFoundException {
        String token = userService.login(user);
        if (token != null) {
            Number maxAge = 1000 * 60 * 60 * 10; // 10 hour
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(maxAge.intValue());
            response.addCookie(cookie);
        }
        return token;
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "User logged out successfully";
    }
}

