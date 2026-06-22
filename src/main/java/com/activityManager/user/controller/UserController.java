package com.activityManager.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activityManager.user.entity.dto.UpdateUserRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.security.CustomUserDetails;
import com.activityManager.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        UserResponse userResponse = userService.getUserById(currentUser.getId());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
