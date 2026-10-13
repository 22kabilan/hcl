package com.order.exception;

/**
 * InsufficientStockException
 * Checked Exception (extends Exception).
 * Thrown when requested order quantity exceeds available inventory.
 */
public class InsufficientStockException extends Exception {

    private final int availableStock;
    private final int requestedQuantity;

    public InsufficientStockException(String message, int availableStock, int requestedQuantity) {
        super(message);
        this.availableStock = availableStock;
        this.requestedQuantity = requestedQuantity;
    }

    public InsufficientStockException(String message, int availableStock, int requestedQuantity, Throwable cause) {
        super(message, cause);
        this.availableStock = availableStock;
        this.requestedQuantity = requestedQuantity;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
