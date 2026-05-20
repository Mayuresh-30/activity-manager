package com.activityManager.activity.entity.dto;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.ActivityPriority;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ActivityResponse {
    private String id;
    private String title;
    private String description;
    private Activity.ActivityStatus status;
    private String projectId;
    private ActivityPriority priority;
    private LocalDate dueDate;
    private Set<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
}
