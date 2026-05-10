package com.activityManager.user.exception;

/**
 * Exception thrown when a user is not found in the system.
 * This is a business exception that indicates the requested user does not exist.
 */
public class UserNotFoundException extends BaseUserException {
    
    private static final String ERROR_CODE = "USER_NOT_FOUND";
    
    /**
     * Constructor for UserNotFoundException with user ID.
     * 
     * @param userId The ID of the user that was not found
     */
    public UserNotFoundException(String userId) {
        super(ERROR_CODE, 
              "User with ID '" + userId + "' was not found",
              "User not found: " + userId);
    }
    
    /**
     * Private constructor for email-based exceptions.
     * 
     * @param email The email of the user that was not found
     */
    private UserNotFoundException(String email, boolean isEmail) {
        super(ERROR_CODE, 
              "User with email '" + email + "' was not found",
              "User not found by email: " + email);
    }
    
    /**
     * Static factory method for UserNotFoundException with email.
     * 
     * @param email The email of the user that was not found
     * @return UserNotFoundException instance for email-based lookup
     */
    public static UserNotFoundException forEmail(String email) {
        return new UserNotFoundException(email, true);
    }
    
    /**
     * Constructor for UserNotFoundException with custom message.
     * 
     * @param userId The ID of the user that was not found
     * @param customMessage Additional context about the search
     */
    public UserNotFoundException(String userId, String customMessage) {
        super(ERROR_CODE, 
              "User with ID '" + userId + "' was not found. " + customMessage,
              "User not found: " + userId + " - " + customMessage);
    }
    
    /**
     * Constructor for UserNotFoundException with cause.
     * 
     * @param userId The ID of the user that was not found
     * @param cause The underlying exception that caused this exception
     */
    public UserNotFoundException(String userId, Throwable cause) {
        super(ERROR_CODE, 
              "User with ID '" + userId + "' was not found",
              "User not found: " + userId,
              cause);
    }
}
