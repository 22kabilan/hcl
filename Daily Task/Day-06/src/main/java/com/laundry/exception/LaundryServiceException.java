package com.laundry.exception;

/**
 * LaundryServiceException (Day 06 Project Task)
 * Base checked domain exception for the laundry enterprise service.
 */
public class LaundryServiceException extends Exception {

    public LaundryServiceException(String message) {
        super(message);
    }

    public LaundryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
