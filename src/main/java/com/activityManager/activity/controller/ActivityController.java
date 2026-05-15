package com.activityManager.activity.controller;

import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ActivityResponse create(
            @Valid @RequestBody ActivityRequest request,
            @RequestParam String userId
    ) {
        return activityService.create(request, userId);
    }

    @GetMapping
    public List<ActivityResponse> getActivities(@RequestParam Long userId) {
        return activityService.getUserActivities(userId);
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<ActivityResponse> startActivity(@PathVariable Long id) {
        ActivityResponse activityResponse = activityService.startActivity(id);
        return ResponseEntity.ok(activityResponse);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ActivityResponse> completeActivity(@PathVariable Long id) {
        ActivityResponse activityResponse = activityService.completeActivity(id);
        return ResponseEntity.ok(activityResponse);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ActivityResponse> cancelActivity(@PathVariable Long id) {
        ActivityResponse activityResponse = activityService.cancelActivity(id);
        return ResponseEntity.ok(activityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(
            @PathVariable Long id,
            @Valid @RequestBody ActivityRequest request
    ) {
        ActivityResponse activityResponse = activityService.updateActivity(id, request);
        return ResponseEntity.ok(activityResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
