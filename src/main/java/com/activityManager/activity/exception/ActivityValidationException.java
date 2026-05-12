package com.activityManager.activity.exception;

/**
 * Exception thrown when activity data validation fails.
 * This exception is used for business rule violations and data integrity issues.
 */
public class ActivityValidationException extends BaseActivityException {
    
    private static final String ERROR_CODE = "ACTIVITY_VALIDATION_FAILED";
    
    private final String fieldName;
    private final Object fieldValue;
    
    /**
     * Constructor for ActivityValidationException with field information.
     * 
     * @param fieldName The name of the field that failed validation
     * @param fieldValue The value that failed validation
     * @param validationMessage The validation error message
     */
    public ActivityValidationException(String fieldName, Object fieldValue, String validationMessage) {
        super(ERROR_CODE, 
              "Validation failed for field '" + fieldName + "': " + validationMessage,
              "Activity validation error - Field: " + fieldName + ", Value: " + fieldValue + ", Error: " + validationMessage);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    /**
     * Constructor for ActivityValidationException with general message.
     * 
     * @param validationMessage The validation error message
     */
    public ActivityValidationException(String validationMessage) {
        super(ERROR_CODE, 
              "Activity validation failed: " + validationMessage,
              "Activity validation error: " + validationMessage);
        this.fieldName = null;
        this.fieldValue = null;
    }
    
    /**
     * Constructor for ActivityValidationException with cause.
     * 
     * @param fieldName The name of the field that failed validation
     * @param fieldValue The value that failed validation
     * @param validationMessage The validation error message
     * @param cause The underlying exception that caused this exception
     */
    public ActivityValidationException(String fieldName, Object fieldValue, String validationMessage, Throwable cause) {
        super(ERROR_CODE, 
              "Validation failed for field '" + fieldName + "': " + validationMessage,
              "Activity validation error - Field: " + fieldName + ", Value: " + fieldValue + ", Error: " + validationMessage,
              cause);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    /**
     * Gets the name of the field that failed validation.
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * Gets the value that failed validation.
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}
