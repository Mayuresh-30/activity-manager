package com.activityManager.activity.service;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityService {
    ActivityResponse create(String userId, ActivityRequest request);

    Page<ActivityResponse> getUserActivities(String userId, Activity.ActivityStatus status, Pageable pageable);

    ActivityResponse getActivityById(String userId, String activityId);

    ActivityResponse startActivity(String userId, String activityId);

    ActivityResponse completeActivity(String userId, String activityId);

    ActivityResponse cancelActivity(String userId, String activityId);

    ActivityResponse updateActivity(String userId, String activityId, ActivityRequest request);

    void deleteActivity(String userId, String activityId);
}
