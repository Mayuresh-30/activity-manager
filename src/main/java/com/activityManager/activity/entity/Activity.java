package com.activityManager.activity.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "activities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Activity status is required")
    private ActivityStatus status;

    private String projectId;
    private ActivityPriority priority;
    private LocalDate dueDate;

    @Builder.Default
    private Set<String> tags = new HashSet<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public enum ActivityStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}
