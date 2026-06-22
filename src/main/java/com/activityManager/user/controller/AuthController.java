package com.activityManager.user.controller;

import com.activityManager.user.entity.dto.AuthResponse;
import com.activityManager.user.entity.dto.UserLoginRequest;
import com.activityManager.user.entity.dto.UserRegisterRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
