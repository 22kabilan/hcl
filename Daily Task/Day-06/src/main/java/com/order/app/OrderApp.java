package com.order.app;

import com.order.exception.InvalidQuantityException;
import com.order.exception.OrderProcessingException;
import com.order.service.OrderProcessor;

/**
 * OrderApp
 * Demonstrates:
 * - Handling checked exceptions wrapped in chained OrderProcessingException
 * - Handling unchecked InvalidQuantityException
 * - Graceful recovery where application continues running without crash
 * - Top-down examination of exception causes
 */
public class OrderApp {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     DAY 06: EXCEPTION HANDLING DEMONSTRATION     ");
        System.out.println("==================================================");

        OrderProcessor processor = new OrderProcessor();

        // Scenario 1: Valid Order Execution
        System.out.println("\n--- Scenario 1: Successful Order ---");
        try {
            processor.processOrder("ORD-001", "SHIRT", 5, 2000.0, 400.0);
            System.out.println(">> Result: Order ORD-001 placed successfully!");
        } catch (OrderProcessingException e) {
            System.err.println(">> Failed: " + e.getMessage());
        }

        // Scenario 2: Unchecked Exception (Negative / Zero Quantity)
        System.out.println("\n--- Scenario 2: Unchecked InvalidQuantityException ---");
        try {
            processor.processOrder("ORD-002", "SUIT", -2, 3000.0, 1500.0);
        } catch (InvalidQuantityException e) {
            System.err.printf(">> Recovered from Unchecked Exception: %s (Attempted Qty: %d)%n",
                    e.getMessage(), e.getAttemptedQuantity());
            System.out.println(">> UI Notice: Please provide a positive quantity value.");
        } catch (OrderProcessingException e) {
            System.err.println(">> Caught chained order error: " + e.getMessage());
        }

        // Scenario 3: Checked Exception Chained (Insufficient Stock)
        System.out.println("\n--- Scenario 3: Checked InsufficientStockException (Chained) ---");
        try {
            // Asking for 20 suits when only 4 exist
            processor.processOrder("ORD-003", "SUIT", 20, 30000.0, 1500.0);
        } catch (OrderProcessingException e) {
            System.err.println(">> High-Level Exception: " + e.getMessage());
            Throwable cause = e.getCause();
            if (cause != null) {
                System.err.println(">> Preserved Root Cause : " + cause.getClass().getSimpleName() + " -> " + cause.getMessage());
            }
            System.out.println(">> Recovery: Order cancelled. Customer notified of low warehouse inventory.");
        }

        // Scenario 4: Checked Exception Chained (Payment Settlement Failure)
        System.out.println("\n--- Scenario 4: Checked PaymentFailedException (Chained) ---");
        try {
            // Price required: 2 * 800 = 1600, but only 500 tendered
            processor.processOrder("ORD-004", "BEDSHEET", 2, 500.0, 800.0);
        } catch (OrderProcessingException e) {
            System.err.println(">> High-Level Exception: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println(">> Preserved Root Cause : " + e.getCause().getClass().getSimpleName() + " -> " + e.getCause().getMessage());
            }
            System.out.println(">> Recovery: Prompted customer for alternate payment method.");
        }

        System.out.println("\n==================================================");
        System.out.println("   ALL SCENARIOS COMPLETED WITH FULL RECOVERY!   ");
        System.out.println("==================================================");
    }
}
