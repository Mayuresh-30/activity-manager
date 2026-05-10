package com.activityManager.activity.exception;

/**
 * Exception thrown when a user tries to access an activity they don't have permission for.
 * This exception ensures proper authorization and access control.
 */
public class ActivityAccessDeniedException extends BaseActivityException {
    
    private static final String ERROR_CODE = "ACTIVITY_ACCESS_DENIED";
    
    private final String activityId;
    private final String userId;
    
    /**
     * Constructor for ActivityAccessDeniedException.
     * 
     * @param activityId The ID of the activity being accessed
     * @param userId The ID of the user attempting access
     */
    public ActivityAccessDeniedException(String activityId, String userId) {
        super(ERROR_CODE, 
              "You don't have permission to access this activity",
              "Access denied for user " + userId + " to activity " + activityId);
        this.activityId = activityId;
        this.userId = userId;
    }
    
    /**
     * Constructor for ActivityAccessDeniedException with custom message.
     * 
     * @param activityId The ID of the activity being accessed
     * @param userId The ID of the user attempting access
     * @param customMessage Additional context about the access denial
     */
    public ActivityAccessDeniedException(String activityId, String userId, String customMessage) {
        super(ERROR_CODE, 
              "You don't have permission to access this activity. " + customMessage,
              "Access denied for user " + userId + " to activity " + activityId + " - " + customMessage);
        this.activityId = activityId;
        this.userId = userId;
    }
    
    /**
     * Constructor for ActivityAccessDeniedException with cause.
     * 
     * @param activityId The ID of the activity being accessed
     * @param userId The ID of the user attempting access
     * @param cause The underlying exception that caused this exception
     */
    public ActivityAccessDeniedException(String activityId, String userId, Throwable cause) {
        super(ERROR_CODE, 
              "You don't have permission to access this activity",
              "Access denied for user " + userId + " to activity " + activityId,
              cause);
        this.activityId = activityId;
        this.userId = userId;
    }
    
    /**
     * Gets the ID of the activity being accessed.
     */
    public String getActivityId() {
        return activityId;
    }
    
    /**
     * Gets the ID of the user attempting access.
     */
    public String getUserId() {
        return userId;
    }
}
