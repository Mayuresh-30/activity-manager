package com.activityManager.user.exception;

/**
 * Exception thrown when user authentication fails.
 * This exception is used for login failures, invalid credentials, and authentication issues.
 */
public class UserAuthenticationException extends BaseUserException {
    
    private static final String ERROR_CODE = "AUTHENTICATION_FAILED";
    
    private final String email;
    
    /**
     * Constructor for UserAuthenticationException with email.
     * 
     * @param email The email used for authentication
     */
    public UserAuthenticationException(String email) {
        super(ERROR_CODE, 
              "Invalid email or password",
              "Authentication failed for email: " + email);
        this.email = email;
    }
    
    /**
     * Constructor for UserAuthenticationException with custom message.
     * 
     * @param email The email used for authentication
     * @param customMessage Additional context about the authentication failure
     */
    public UserAuthenticationException(String email, String customMessage) {
        super(ERROR_CODE, 
              "Authentication failed. " + customMessage,
              "Authentication failed for email: " + email + " - " + customMessage);
        this.email = email;
    }
    
    /**
     * Constructor for UserAuthenticationException with cause.
     * 
     * @param email The email used for authentication
     * @param cause The underlying exception that caused this exception
     */
    public UserAuthenticationException(String email, Throwable cause) {
        super(ERROR_CODE, 
              "Invalid email or password",
              "Authentication failed for email: " + email,
              cause);
        this.email = email;
    }
    
    /**
     * Constructor for UserAuthenticationException for general authentication issues.
     * 
     * @param reason The reason for authentication failure
     */
    public UserAuthenticationException(String email, String reason, boolean isGeneric) {
        super(ERROR_CODE, 
              reason,
              "Authentication issue: " + reason);
        this.email = email;
    }
    
    /**
     * Gets the email used for authentication.
     */
    public String getEmail() {
        return email;
    }
}
