package com.order.exception;

/**
 * PaymentFailedException
 * Checked Exception representing gateway or balance settlement failures.
 */
public class PaymentFailedException extends Exception {

    private final double attemptedAmount;

    public PaymentFailedException(String message, double attemptedAmount) {
        super(message);
        this.attemptedAmount = attemptedAmount;
    }

    public PaymentFailedException(String message, double attemptedAmount, Throwable cause) {
        super(message, cause);
        this.attemptedAmount = attemptedAmount;
    }

    public double getAttemptedAmount() {
        return attemptedAmount;
    }
}
