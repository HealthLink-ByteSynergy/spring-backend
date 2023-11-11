package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User_Entity;
import com.example.demo.exception.User_DuplicateEmailException;
import com.example.demo.exception.User_NotFoundException;
import com.example.demo.exception.User_WrongPasswordException;
import com.example.demo.service.User_Service;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class User_Controller {
    
    private final User_Service userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User_Entity user) throws User_DuplicateEmailException {
        return userService.signup(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User_Entity user, HttpServletResponse response) throws User_WrongPasswordException, User_NotFoundException {
        String token = userService.login(user);
        if (token != null) {
            // TODO: set cookie
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

