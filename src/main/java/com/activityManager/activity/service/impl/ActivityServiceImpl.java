package com.activityManager.activity.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.exception.ActivityNotFoundException;
import com.activityManager.activity.exception.ActivityStatusException;
import com.activityManager.activity.repository.ActivityRepo;
import com.activityManager.activity.service.ActivityService;
import com.activityManager.user.exception.UserNotFoundException;
import com.activityManager.user.entity.User;
import com.activityManager.user.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepo repo;
    private final UserRepo userRepo;

    @Override
    public ActivityResponse create(ActivityRequest request, String userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Activity activity = Activity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .userId(user.getId())
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

    @Override
    public ActivityResponse updateActivity(Long id, ActivityRequest request) {
        String activityIdStr = String.valueOf(id);
        Activity activity = repo.findById(activityIdStr)
                .orElseThrow(() -> new ActivityNotFoundException(activityIdStr));
        
        // Update only the fields that are provided in the request
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            activity.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            activity.setDescription(request.getDescription());
        }
        
        Activity savedActivity = repo.save(activity);
        return convertToResponse(savedActivity);
    }

    @Override
    public void deleteActivity(Long id) {
        String activityIdStr = String.valueOf(id);
        Activity activity = repo.findById(activityIdStr)
                .orElseThrow(() -> new ActivityNotFoundException(activityIdStr));
        
        // Delete the activity regardless of its status
        repo.delete(activity);
    }
}
