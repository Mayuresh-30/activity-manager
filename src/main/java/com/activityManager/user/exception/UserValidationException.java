package com.activityManager.user.exception;

/**
 * Exception thrown when user data validation fails.
 * This exception is used for business rule violations and data integrity issues.
 */
public class UserValidationException extends BaseUserException {
    
    private static final String ERROR_CODE = "USER_VALIDATION_FAILED";
    
    private final String fieldName;
    private final Object fieldValue;
    
    /**
     * Constructor for UserValidationException with field information.
     * 
     * @param fieldName The name of the field that failed validation
     * @param fieldValue The value that failed validation
     * @param validationMessage The validation error message
     */
    public UserValidationException(String fieldName, Object fieldValue, String validationMessage) {
        super(ERROR_CODE, 
              "Validation failed for field '" + fieldName + "': " + validationMessage,
              "User validation error - Field: " + fieldName + ", Value: " + fieldValue + ", Error: " + validationMessage);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    /**
     * Constructor for UserValidationException with general message.
     * 
     * @param validationMessage The validation error message
     */
    public UserValidationException(String validationMessage) {
        super(ERROR_CODE, 
              "User validation failed: " + validationMessage,
              "User validation error: " + validationMessage);
        this.fieldName = null;
        this.fieldValue = null;
    }
    
    /**
     * Constructor for UserValidationException with cause.
     * 
     * @param fieldName The name of the field that failed validation
     * @param fieldValue The value that failed validation
     * @param validationMessage The validation error message
     * @param cause The underlying exception that caused this exception
     */
    public UserValidationException(String fieldName, Object fieldValue, String validationMessage, Throwable cause) {
        super(ERROR_CODE, 
              "Validation failed for field '" + fieldName + "': " + validationMessage,
              "User validation error - Field: " + fieldName + ", Value: " + fieldValue + ", Error: " + validationMessage,
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
