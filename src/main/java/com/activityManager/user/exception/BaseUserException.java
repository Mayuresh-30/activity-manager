package com.activityManager.user.exception;

import lombok.Getter;

/**
 * Base exception class for all User-related runtime exceptions.
 * Provides common functionality including error codes, timestamps, and detailed messages.
 */
@Getter
public abstract class BaseUserException extends RuntimeException {
    
    private final String errorCode;
    private final String userMessage;
    private final long timestamp;
    
    /**
     * Constructor for BaseUserException.
     * 
     * @param errorCode Unique error code for identification and debugging
     * @param userMessage User-friendly message explaining the error
     * @param technicalMessage Technical details for developers
     * @param cause Original exception that caused this exception
     */
    protected BaseUserException(String errorCode, String userMessage, String technicalMessage, Throwable cause) {
        super(technicalMessage, cause);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * Constructor for BaseUserException without cause.
     * 
     * @param errorCode Unique error code for identification and debugging
     * @param userMessage User-friendly message explaining the error
     * @param technicalMessage Technical details for developers
     */
    protected BaseUserException(String errorCode, String userMessage, String technicalMessage) {
        this(errorCode, userMessage, technicalMessage, null);
    }
    
    /**
     * Constructor for BaseUserException with single message.
     * 
     * @param errorCode Unique error code for identification and debugging
     * @param message Message that serves both user and technical purposes
     */
    protected BaseUserException(String errorCode, String message) {
        this(errorCode, message, message, null);
    }
    
    /**
     * Returns a detailed error description suitable for logging.
     */
    public String getDetailedErrorDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserException [errorCode=").append(errorCode)
          .append(", userMessage=").append(userMessage)
          .append(", timestamp=").append(timestamp)
          .append(", message=").append(getMessage());
        
        if (getCause() != null) {
            sb.append(", cause=").append(getCause().getClass().getSimpleName())
              .append(": ").append(getCause().getMessage());
        }
        
        sb.append("]");
        return sb.toString();
    }
}
