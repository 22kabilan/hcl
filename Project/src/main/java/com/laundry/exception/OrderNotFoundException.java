package com.laundry.exception;

/**
 * OrderNotFoundException
 * 
 * Thrown when an order lookup by ID fails.
 */
public class OrderNotFoundException extends LaundryServiceException {
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String orderId) {
        super(String.format("Laundry order not found with ID: [%s]", orderId));
    }
}
