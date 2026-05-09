package com.activityManager.activity.service;

import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ActivityService {
    ActivityResponse create(ActivityRequest request, String userId);

    List<ActivityResponse> getUserActivities(Long userId);
}
