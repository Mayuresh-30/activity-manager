package com.activityManager.user.exception;

/**
 * Exception thrown when attempting to create a user that already exists.
 * This exception is used to prevent duplicate user accounts and maintain data integrity.
 */
public class UserAlreadyExistsException extends BaseUserException {
    
    private static final String ERROR_CODE = "USER_ALREADY_EXISTS";
    
    private final String identifier;
    private final String identifierType;
    
    /**
     * Constructor for UserAlreadyExistsException with email.
     * 
     * @param email The email of the user that already exists
     */
    public UserAlreadyExistsException(String email) {
        super(ERROR_CODE, 
              "A user with email '" + email + "' already exists",
              "User already exists with email: " + email);
        this.identifier = email;
        this.identifierType = "email";
    }
    
    /**
     * Constructor for UserAlreadyExistsException with identifier and type.
     * 
     * @param identifier The identifier (email, username, etc.) that already exists
     * @param identifierType The type of identifier (email, username, etc.)
     */
    public UserAlreadyExistsException(String identifier, String identifierType) {
        super(ERROR_CODE, 
              "A user with " + identifierType + " '" + identifier + "' already exists",
              "User already exists with " + identifierType + ": " + identifier);
        this.identifier = identifier;
        this.identifierType = identifierType;
    }
    
    /**
     * Constructor for UserAlreadyExistsException with custom message.
     * 
     * @param identifier The identifier that already exists
     * @param identifierType The type of identifier
     * @param customMessage Additional context about the conflict
     */
    public UserAlreadyExistsException(String identifier, String identifierType, String customMessage) {
        super(ERROR_CODE, 
              "A user with " + identifierType + " '" + identifier + "' already exists. " + customMessage,
              "User already exists with " + identifierType + ": " + identifier + " - " + customMessage);
        this.identifier = identifier;
        this.identifierType = identifierType;
    }
    
    /**
     * Constructor for UserAlreadyExistsException with cause.
     * 
     * @param identifier The identifier that already exists
     * @param identifierType The type of identifier
     * @param cause The underlying exception that caused this exception
     */
    public UserAlreadyExistsException(String identifier, String identifierType, Throwable cause) {
        super(ERROR_CODE, 
              "A user with " + identifierType + " '" + identifier + "' already exists",
              "User already exists with " + identifierType + ": " + identifier,
              cause);
        this.identifier = identifier;
        this.identifierType = identifierType;
    }
    
    /**
     * Gets the identifier that caused the conflict.
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * Gets the type of identifier that caused the conflict.
     */
    public String getIdentifierType() {
        return identifierType;
    }
}
