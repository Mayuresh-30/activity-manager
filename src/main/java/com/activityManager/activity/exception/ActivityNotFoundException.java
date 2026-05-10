package com.activityManager.activity.exception;

/**
 * Exception thrown when an activity is not found in the system.
 * This is a business exception that indicates the requested activity does not exist.
 */
public class ActivityNotFoundException extends BaseActivityException {
    
    private static final String ERROR_CODE = "ACTIVITY_NOT_FOUND";
    
    /**
     * Constructor for ActivityNotFoundException with activity ID.
     * 
     * @param activityId The ID of the activity that was not found
     */
    public ActivityNotFoundException(String activityId) {
        super(ERROR_CODE, 
              "Activity with ID '" + activityId + "' was not found",
              "Activity not found: " + activityId);
    }
    
    /**
     * Constructor for ActivityNotFoundException with custom message.
     * 
     * @param activityId The ID of the activity that was not found
     * @param customMessage Additional context about the search
     */
    public ActivityNotFoundException(String activityId, String customMessage) {
        super(ERROR_CODE, 
              "Activity with ID '" + activityId + "' was not found. " + customMessage,
              "Activity not found: " + activityId + " - " + customMessage);
    }
    
    /**
     * Constructor for ActivityNotFoundException with cause.
     * 
     * @param activityId The ID of the activity that was not found
     * @param cause The underlying exception that caused this exception
     */
    public ActivityNotFoundException(String activityId, Throwable cause) {
        super(ERROR_CODE, 
              "Activity with ID '" + activityId + "' was not found",
              "Activity not found: " + activityId,
              cause);
    }
}
