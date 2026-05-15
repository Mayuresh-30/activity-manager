package com.activityManager.user.controller;

import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.user.service.UserActivityService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserActivityService userActivityService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/activity/{userId}")
    public ResponseEntity<List<ActivityResponse>> getAllActivityByUserId(@PathVariable String userId ){
        List<ActivityResponse> allActivities = userActivityService.getAllActivitiesByUserId(userId);
        return ResponseEntity.ok(allActivities);
    }
}
