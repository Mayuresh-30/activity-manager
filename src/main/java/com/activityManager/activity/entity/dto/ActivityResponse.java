package com.activityManager.activity.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityResponse {
    private String id;
    private String title;
    private String description;
}
