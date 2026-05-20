package com.activityManager.user.service;

import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.entity.dto.CreateAdminRequest;

import java.util.List;

public interface AdminService {
    UserResponse createAdmin(CreateAdminRequest request);
    List<UserResponse> getAllUsers();
}
