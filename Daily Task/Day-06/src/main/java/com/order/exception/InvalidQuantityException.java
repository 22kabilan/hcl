package com.order.exception;

/**
 * InvalidQuantityException
 * Unchecked Exception (extends RuntimeException).
 * Thrown when an order contains a non-positive or malformed quantity.
 */
public class InvalidQuantityException extends RuntimeException {

    private final int attemptedQuantity;

    public InvalidQuantityException(String message, int attemptedQuantity) {
        super(message);
        this.attemptedQuantity = attemptedQuantity;
    }

    public InvalidQuantityException(String message, int attemptedQuantity, Throwable cause) {
        super(message, cause);
        this.attemptedQuantity = attemptedQuantity;
    }

    public int getAttemptedQuantity() {
        return attemptedQuantity;
    }
}
