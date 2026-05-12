package com.activityManager.user.service.impl;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.exception.ActivityNotFoundException;
import com.activityManager.activity.repository.ActivityRepo;
import com.activityManager.user.entity.User;
import com.activityManager.user.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final ActivityRepo activityRepo;

    @Override
    public List<Activity> getAllActivitiesByUserId(String userId) {
        return activityRepo.findByUserId(userId);
    }

    @Override
    public Activity getActivityById(String activityId) {
        return activityRepo.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException(activityId));
    }

    @Override
    public List<Activity> getAllActivitiesByUser(User user) {
        return activityRepo.findByUserId(user.getId());
    }

    @Override
    public List<Activity> getActivitiesByUserIdAndStatus(String userId, Activity.ActivityStatus status) {
        List<Activity> userActivities = activityRepo.findByUserId(userId);
        return userActivities.stream()
                .filter(activity -> activity.getStatus() == status)
                .toList();
    }

    @Override
    public Activity updateActivityStatus(String activityId, Activity.ActivityStatus status) {
        Activity activity = getActivityById(activityId);
        activity.setStatus(status);
        return activityRepo.save(activity);
    }
}
