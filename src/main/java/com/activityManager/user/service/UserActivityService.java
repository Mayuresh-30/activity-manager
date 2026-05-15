package com.activityManager.user.service;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.user.entity.User;
import com.activityManager.user.entity.dto.UserResponse;

import java.util.List;

public interface UserActivityService {
    
    /**
     * Get all activities for a specific user
     * @param userId the ID of the user
     * @return list of activities belonging to the user
     */
    List<ActivityResponse> getAllActivitiesByUserId(String userId);
    
    /**
     * Get activity by activity ID
     * @param activityId the ID of the activity
     * @return the activity with the specified ID
     */
    ActivityResponse getActivityById(String activityId);
    
    /**
     * Get all activities for a specific user by user entity
     * @param user the user entity
     * @return list of activities belonging to the user
     */
    List<ActivityResponse> getAllActivitiesByUser(User user);
    
    /**
     * Get activities for a user by status
     * @param userId the ID of the user
     * @param status the activity status to filter by
     * @return list of activities with the specified status for the user
     */
    List<ActivityResponse> getActivitiesByUserIdAndStatus(String userId, Activity.ActivityStatus status);
    
    /**
     * Update activity status
     * @param activityId the ID of the activity
     * @param status the new status to set
     * @return the updated activity
     */
    ActivityResponse updateActivityStatus(String activityId, Activity.ActivityStatus status);
    
    /**
     * Get user with their activities
     * @param userId the ID of the user
     * @return user response with activities
     */
    UserResponse getUserWithActivities(String userId);
}
