package com.activityManager.user.service.impl;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.exception.ActivityNotFoundException;
import com.activityManager.activity.repository.ActivityRepo;
import com.activityManager.user.entity.User;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.exception.UserNotFoundException;
import com.activityManager.user.repository.UserRepo;
import com.activityManager.user.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final ActivityRepo activityRepo;
    private final UserRepo userRepository;

    @Override
    public List<ActivityResponse> getAllActivitiesByUserId(String userId) {
        return activityRepo.findByUserId(userId).stream()
                .map(this::convertToActivityResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse getActivityById(String activityId) {
        Activity activity = activityRepo.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException(activityId));
        return convertToActivityResponse(activity);
    }

    @Override
    public List<ActivityResponse> getAllActivitiesByUser(User user) {
        return activityRepo.findByUserId(user.getId()).stream()
                .map(this::convertToActivityResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityResponse> getActivitiesByUserIdAndStatus(String userId, Activity.ActivityStatus status) {
        List<Activity> userActivities = activityRepo.findByUserId(userId);
        return userActivities.stream()
                .filter(activity -> activity.getStatus() == status)
                .map(this::convertToActivityResponse)
                .toList();
    }

    @Override
    public ActivityResponse updateActivityStatus(String activityId, Activity.ActivityStatus status) {
        Activity activity = activityRepo.findById(activityId).orElseThrow(() -> new ActivityNotFoundException(activityId));
        activity.setStatus(status);
        return convertToActivityResponse(activityRepo.save(activity));
    }

    @Override
    public UserResponse getUserWithActivities(String userId) {
        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Get user's activities
        List<ActivityResponse> activities = activityRepo.findByUserId(userId)
                .stream()
                .map(this::convertToActivityResponse)
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .message("User with activities retrieved successfully")
                .build();
    }

    private ActivityResponse convertToActivityResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .status(activity.getStatus())
                .build();
    }
}
