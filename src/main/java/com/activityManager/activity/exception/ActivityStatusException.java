package com.activityManager.activity.exception;

import com.activityManager.activity.entity.Activity;

/**
 * Exception thrown when an invalid activity status transition is attempted.
 * This exception ensures that activities follow proper state transitions.
 */
public class ActivityStatusException extends BaseActivityException {
    
    private static final String ERROR_CODE = "INVALID_ACTIVITY_STATUS";
    
    private final Activity.ActivityStatus currentStatus;
    private final Activity.ActivityStatus targetStatus;
    
    /**
     * Constructor for ActivityStatusException.
     * 
     * @param activityId The ID of the activity
     * @param currentStatus The current status of the activity
     * @param targetStatus The status that was attempted to be set
     */
    public ActivityStatusException(String activityId, Activity.ActivityStatus currentStatus, Activity.ActivityStatus targetStatus) {
        super(ERROR_CODE, 
              "Cannot change activity status from '" + currentStatus + "' to '" + targetStatus + "'",
              "Invalid status transition for activity " + activityId + ": " + currentStatus + " -> " + targetStatus);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }
    
    /**
     * Constructor for ActivityStatusException with custom message.
     * 
     * @param activityId The ID of the activity
     * @param currentStatus The current status of the activity
     * @param targetStatus The status that was attempted to be set
     * @param customMessage Additional context about the invalid transition
     */
    public ActivityStatusException(String activityId, Activity.ActivityStatus currentStatus, Activity.ActivityStatus targetStatus, String customMessage) {
        super(ERROR_CODE, 
              "Cannot change activity status from '" + currentStatus + "' to '" + targetStatus + "'. " + customMessage,
              "Invalid status transition for activity " + activityId + ": " + currentStatus + " -> " + targetStatus + " - " + customMessage);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }
    
    /**
     * Constructor for ActivityStatusException with cause.
     * 
     * @param activityId The ID of the activity
     * @param currentStatus The current status of the activity
     * @param targetStatus The status that was attempted to be set
     * @param cause The underlying exception that caused this exception
     */
    public ActivityStatusException(String activityId, Activity.ActivityStatus currentStatus, Activity.ActivityStatus targetStatus, Throwable cause) {
        super(ERROR_CODE, 
              "Cannot change activity status from '" + currentStatus + "' to '" + targetStatus + "'",
              "Invalid status transition for activity " + activityId + ": " + currentStatus + " -> " + targetStatus,
              cause);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }
    
    /**
     * Gets the current status of the activity.
     */
    public Activity.ActivityStatus getCurrentStatus() {
        return currentStatus;
    }
    
    /**
     * Gets the target status that was attempted.
     */
    public Activity.ActivityStatus getTargetStatus() {
        return targetStatus;
    }
}
