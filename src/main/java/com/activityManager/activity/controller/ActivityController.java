package com.activityManager.activity.controller;

import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ActivityResponse create(
            @RequestBody ActivityRequest request,
            @RequestParam String userId
    ) {
        return activityService.create(request, userId);
    }

    @GetMapping
    public List<ActivityResponse> getActivities(@RequestParam Long userId) {
        return activityService.getUserActivities(userId);
    }
}
