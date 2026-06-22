package com.activityManager.user.service;

import com.activityManager.user.entity.dto.AuthResponse;
import com.activityManager.user.entity.dto.UserLoginRequest;
import com.activityManager.user.entity.dto.UserRegisterRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.entity.dto.UpdateUserRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest request);

    AuthResponse login(UserLoginRequest request);

    UserResponse getUserById(String id);

    UserResponse getUserByEmail(String email);

    UserResponse updateUser(String id, UpdateUserRequest request);

    void deleteUser(String id);
}
