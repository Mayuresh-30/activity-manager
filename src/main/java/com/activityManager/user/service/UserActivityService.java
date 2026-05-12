package com.activityManager.user.service;

import com.activityManager.activity.entity.Activity;
import com.activityManager.user.entity.User;

import java.util.List;

public interface UserActivityService {
    
    /**
     * Get all activities for a specific user
     * @param userId the ID of the user
     * @return list of activities belonging to the user
     */
    List<Activity> getAllActivitiesByUserId(String userId);
    
    /**
     * Get activity by activity ID
     * @param activityId the ID of the activity
     * @return the activity with the specified ID
     */
    Activity getActivityById(String activityId);
    
    /**
     * Get all activities for a specific user by user entity
     * @param user the user entity
     * @return list of activities belonging to the user
     */
    List<Activity> getAllActivitiesByUser(User user);
    
    /**
     * Get activities for a user by status
     * @param userId the ID of the user
     * @param status the activity status to filter by
     * @return list of activities with the specified status for the user
     */
    List<Activity> getActivitiesByUserIdAndStatus(String userId, Activity.ActivityStatus status);
    
    /**
     * Update activity status
     * @param activityId the ID of the activity
     * @param status the new status to set
     * @return the updated activity
     */
    Activity updateActivityStatus(String activityId, Activity.ActivityStatus status);
}
