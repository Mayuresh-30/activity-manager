package com.activityManager.activity.service.impl;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.exception.ActivityNotFoundException;
import com.activityManager.activity.exception.ActivityStatusException;
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
                .status(Activity.ActivityStatus.PENDING)
                .build();

        repo.save(activity);

        ActivityResponse response = ActivityResponse.builder()
                .id(activity.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .status(activity.getStatus())
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
                .status(activity.getStatus())
                .build();
    }

    @Override
    public ActivityResponse startActivity(Long id) {
        String activityIdStr = String.valueOf(id);
        Activity activity = repo.findById(activityIdStr)
                .orElseThrow(() -> new ActivityNotFoundException(activityIdStr));
        
        // Validate status transition
        if (activity.getStatus() != Activity.ActivityStatus.PENDING) {
            throw new ActivityStatusException(activityIdStr, activity.getStatus(), Activity.ActivityStatus.IN_PROGRESS,
                    "Only pending activities can be started");
        }
        
        activity.setStatus(Activity.ActivityStatus.IN_PROGRESS);
        Activity savedActivity = repo.save(activity);
        return convertToResponse(savedActivity);
    }

    @Override
    public ActivityResponse completeActivity(Long id) {
        String activityIdStr = String.valueOf(id);
        Activity activity = repo.findById(activityIdStr)
                .orElseThrow(() -> new ActivityNotFoundException(activityIdStr));
        
        // Validate status transition
        if (activity.getStatus() != Activity.ActivityStatus.IN_PROGRESS) {
            throw new ActivityStatusException(activityIdStr, activity.getStatus(), Activity.ActivityStatus.COMPLETED,
                    "Only in-progress activities can be completed");
        }
        
        activity.setStatus(Activity.ActivityStatus.COMPLETED);
        Activity savedActivity = repo.save(activity);
        return convertToResponse(savedActivity);
    }

    @Override
    public ActivityResponse cancelActivity(Long id) {
        String activityIdStr = String.valueOf(id);
        Activity activity = repo.findById(activityIdStr)
                .orElseThrow(() -> new ActivityNotFoundException(activityIdStr));
        
        // Validate status transition
        if (activity.getStatus() == Activity.ActivityStatus.COMPLETED) {
            throw new ActivityStatusException(activityIdStr, activity.getStatus(), Activity.ActivityStatus.CANCELLED,
                    "Completed activities cannot be cancelled");
        }
        
        if (activity.getStatus() == Activity.ActivityStatus.CANCELLED) {
            throw new ActivityStatusException(activityIdStr, activity.getStatus(), Activity.ActivityStatus.CANCELLED,
                    "Activity is already cancelled");
        }
        
        activity.setStatus(Activity.ActivityStatus.CANCELLED);
        Activity savedActivity = repo.save(activity);
        return convertToResponse(savedActivity);
    }
}
