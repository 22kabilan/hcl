package com.order.service;

import com.order.exception.InsufficientStockException;
import com.order.exception.InvalidQuantityException;
import com.order.exception.OrderProcessingException;
import com.order.exception.PaymentFailedException;

import java.util.HashMap;
import java.util.Map;

/**
 * OrderProcessor
 * Demonstrates:
 * - try-catch-finally semantics
 * - throw vs throws
 * - Checked vs Unchecked custom exceptions
 * - Multi-catch block
 * - Exception Chaining (preserving original root cause)
 * - Mandatory finally audit logging
 */
public class OrderProcessor {

    private final Map<String, Integer> inventory = new HashMap<>();

    public OrderProcessor() {
        // Initial inventory stock
        inventory.put("SHIRT", 15);
        inventory.put("SUIT", 4);
        inventory.put("BEDSHEET", 8);
    }

    /**
     * Processes an order for a given item and quantity.
     * Demonstrates checked exception declaration (throws) and runtime validation (throw).
     */
    public boolean processOrder(String orderId, String itemType, int quantity, double paymentAmount, double itemUnitPrice)
            throws OrderProcessingException {

        System.out.printf("%n[AUDIT START] Initiating transaction for Order %s: %d x %s...%n", orderId, quantity, itemType);
        boolean auditSuccess = false;

        try {
            // 1. Unchecked Exception validation (InvalidQuantityException)
            if (quantity <= 0) {
                throw new InvalidQuantityException(
                        "Order quantity must be positive. Received invalid quantity: " + quantity, quantity);
            }

            // 2. Checked Exception validation (InsufficientStockException)
            int currentStock = inventory.getOrDefault(itemType, 0);
            if (quantity > currentStock) {
                throw new InsufficientStockException(
                        String.format("Requested %d units of '%s', but only %d are available in warehouse.",
                                quantity, itemType, currentStock),
                        currentStock, quantity);
            }

            // 3. Payment settlement check (PaymentFailedException)
            double totalPayable = quantity * itemUnitPrice;
            if (paymentAmount < totalPayable) {
                throw new PaymentFailedException(
                        String.format("Insufficient payment: required ₹%.2f, but tendered ₹%.2f.",
                                totalPayable, paymentAmount),
                        paymentAmount);
            }

            // Deduct inventory upon valid transaction
            inventory.put(itemType, currentStock - quantity);
            System.out.printf(">> [SUCCESS] Deducted %d units of '%s'. Remaining stock: %d%n",
                    quantity, itemType, inventory.get(itemType));
            auditSuccess = true;
            return true;

        } catch (InsufficientStockException | PaymentFailedException underlyingException) {
            // Multi-catch block handling diverse checked failures
            System.err.printf(">> [PIPELINE ERROR] Multi-catch caught %s: %s%n",
                    underlyingException.getClass().getSimpleName(), underlyingException.getMessage());

            // Exception chaining: Wraps underlying checked exception while preserving root cause
            throw new OrderProcessingException(
                    orderId,
                    "Order fulfillment failed during validation phase.",
                    underlyingException);

        } finally {
            // Finally audit block: Guarantees execution in all circumstances
            System.out.printf("[AUDIT LOG] Order %s completion status: %s. Audit ledger closed.%n",
                    orderId, (auditSuccess ? "COMPLETED" : "FAILED / ROLLED BACK"));
        }
    }

    public int getStock(String item) {
        return inventory.getOrDefault(item, 0);
    }
}
