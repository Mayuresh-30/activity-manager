package com.activityManager.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Standard error response DTO for all API endpoints.
 * Provides consistent error structure across the application.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * HTTP status code of the error.
     */
    private int status;
    
    /**
     * Application-specific error code for debugging and monitoring.
     */
    private String errorCode;
    
    /**
     * User-friendly error message explaining what went wrong.
     */
    private String message;
    
    /**
     * Detailed technical information for debugging (optional).
     */
    private String details;
    
    /**
     * Timestamp when the error occurred.
     */
    private Instant timestamp;
    
    /**
     * Path of the API endpoint that generated the error.
     */
    private String path;
    
    /**
     * List of field-specific validation errors (optional).
     */
    private List<FieldError> fieldErrors;
    
    /**
     * Request ID for tracking (optional).
     */
    private String requestId;
    
    /**
     * Represents a field-specific validation error.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        
        /**
         * Name of the field that failed validation.
         */
        private String field;
        
        /**
         * Rejected value.
         */
        private Object rejectedValue;
        
        /**
         * Error message for the field.
         */
        private String message;
        
        /**
         * Error code for the field validation.
         */
        private String code;
    }
    
    /**
     * Creates a simple error response with basic information.
     * 
     * @param status HTTP status code
     * @param errorCode Application error code
     * @param message User-friendly message
     * @param path Request path
     * @return ErrorResponse instance
     */
    public static ErrorResponse of(int status, String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .status(status)
                .errorCode(errorCode)
                .message(message)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
    
    /**
     * Creates a detailed error response with technical details.
     * 
     * @param status HTTP status code
     * @param errorCode Application error code
     * @param message User-friendly message
     * @param details Technical details
     * @param path Request path
     * @return ErrorResponse instance
     */
    public static ErrorResponse of(int status, String errorCode, String message, String details, String path) {
        return ErrorResponse.builder()
                .status(status)
                .errorCode(errorCode)
                .message(message)
                .details(details)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
    
    /**
     * Creates an error response with field validation errors.
     * 
     * @param status HTTP status code
     * @param errorCode Application error code
     * @param message User-friendly message
     * @param fieldErrors List of field validation errors
     * @param path Request path
     * @return ErrorResponse instance
     */
    public static ErrorResponse of(int status, String errorCode, String message, List<FieldError> fieldErrors, String path) {
        return ErrorResponse.builder()
                .status(status)
                .errorCode(errorCode)
                .message(message)
                .fieldErrors(fieldErrors)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
}
