package com.activityManager.activity.entity.dto;

import com.activityManager.activity.entity.ActivityPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ActivityRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private String projectId;
    private ActivityPriority priority;
    private LocalDate dueDate;
    private Set<String> tags;
}
