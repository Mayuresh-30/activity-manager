package com.activityManager.activity.service.impl;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.repository.ActivityRepo;
import com.activityManager.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepo repo;

    @Override
    public ActivityResponse create(ActivityRequest request, String userId){
        Activity activity = Activity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .userId(userId)
                .build();

        repo.save(activity);

        ActivityResponse response = ActivityResponse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        return response;
    }

    @Override
    public List<ActivityResponse> getUserActivities(Long userId){
        String userIdStr = String.valueOf(userId);

        // Find all activities for this user
        List<Activity> activities = repo.findByUserId(userIdStr);

        // Convert List<Activity> → List<ActivityResponse>
        return activities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList()); }

    //helper method
    private ActivityResponse convertToResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .build();
    }
}
