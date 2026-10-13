package com.order.exception;

/**
 * OrderProcessingException
 * Checked root domain exception demonstrating Exception Chaining.
 * Preserves the underlying root cause (e.g., InsufficientStockException or PaymentFailedException).
 */
public class OrderProcessingException extends Exception {

    private final String orderId;

    public OrderProcessingException(String orderId, String message, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
