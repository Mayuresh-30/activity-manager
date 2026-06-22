package com.activityManager.user.controller;

import com.activityManager.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.activityManager.user.entity.dto.CreateAdminRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')") 
    @PostMapping("/create-admin")
    public ResponseEntity<UserResponse> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        UserResponse userResponse = adminService.createAdmin(request);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> userResponse = adminService.getAllUsers();
        return ResponseEntity.ok(userResponse);
    }
}
