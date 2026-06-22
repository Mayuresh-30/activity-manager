package com.activityManager.activity.controller;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.service.ActivityService;
import com.activityManager.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> create(
            @PathVariable String userId,
            @Valid @RequestBody ActivityRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        ActivityResponse created = activityService.create(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<ActivityResponse>> getActivities(
            @PathVariable String userId,
            @RequestParam(required = false) Activity.ActivityStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        Page<ActivityResponse> activities = activityService.getUserActivities(
                userId,
                status,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(
            @PathVariable String userId,
            @PathVariable String activityId,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        ActivityResponse activityResponse = activityService.getActivityById(userId, activityId);
        return ResponseEntity.ok(activityResponse);
    }

    @PatchMapping("/{activityId}/start")
    public ResponseEntity<ActivityResponse> startActivity(
            @PathVariable String userId,
            @PathVariable String activityId,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        ActivityResponse activityResponse = activityService.startActivity(userId, activityId);
        return ResponseEntity.ok(activityResponse);
    }

    @PatchMapping("/{activityId}/complete")
    public ResponseEntity<ActivityResponse> completeActivity(
            @PathVariable String userId,
            @PathVariable String activityId,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        ActivityResponse activityResponse = activityService.completeActivity(userId, activityId);
        return ResponseEntity.ok(activityResponse);
    }

    @PatchMapping("/{activityId}/cancel")
    public ResponseEntity<ActivityResponse> cancelActivity(
            @PathVariable String userId,
            @PathVariable String activityId,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        ActivityResponse activityResponse = activityService.cancelActivity(userId, activityId);
        return ResponseEntity.ok(activityResponse);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(
            @PathVariable String userId,
            @PathVariable String activityId,
            @Valid @RequestBody ActivityRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        ActivityResponse activityResponse = activityService.updateActivity(userId, activityId, request);
        return ResponseEntity.ok(activityResponse);
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable String userId,
            @PathVariable String activityId,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        verifyAccess(userId, currentUser);
        activityService.deleteActivity(userId, activityId);
        return ResponseEntity.noContent().build();
    }

    private void verifyAccess(String userId, CustomUserDetails currentUser) {
        if (currentUser == null) {
            throw new AccessDeniedException("Authentication required");
        }

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));

        if (!isAdmin && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("You are not authorized to manage activities for this user");
        }
    }
}
