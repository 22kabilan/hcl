package com.laundry.exception;

/**
 * OrderNotFoundException (Day 06 Project Task)
 * Thrown when an order lookup query fails to match existing records.
 */
public class OrderNotFoundException extends LaundryServiceException {

    private final String orderId;

    public OrderNotFoundException(String orderId) {
        super("Order not found with identifier: " + orderId);
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
