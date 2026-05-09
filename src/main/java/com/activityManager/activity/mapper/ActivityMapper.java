package com.activityManager.activity.mapper;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityResponse;

public class ActivityMapper {

    public static ActivityResponse toResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .build();
    }
}
