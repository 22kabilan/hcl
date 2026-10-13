package com.laundry.exception;

/**
 * LaundryServiceException
 * 
 * Base domain exception for the laundry service system.
 * Preserves exception chaining causes.
 * Developed as part of Day 06 Exception Handling.
 */
public class LaundryServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public LaundryServiceException(String message) {
        super(message);
    }

    public LaundryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
