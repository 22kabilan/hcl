import java.util.HashMap;
import java.util.Map;

/**
 * OrderProcessorApp (Day 06 Single-File Standalone Runner)
 * Demonstrates:
 * - Checked InsufficientStockException
 * - Unchecked InvalidQuantityException
 * - Chained causes (OrderProcessingException wrapping root causes)
 * - Multi-catch block
 * - finally audit execution
 * - No empty catch blocks
 */
public class OrderProcessorApp {

    // Checked Exception
    static class InsufficientStockException extends Exception {
        private final int available;
        private final int requested;

        public InsufficientStockException(String msg, int available, int requested) {
            super(msg);
            this.available = available;
            this.requested = requested;
        }

        public int getAvailable() { return available; }
        public int getRequested() { return requested; }
    }

    // Unchecked Exception
    static class InvalidQuantityException extends RuntimeException {
        private final int attemptedQty;

        public InvalidQuantityException(String msg, int attemptedQty) {
            super(msg);
            this.attemptedQty = attemptedQty;
        }

        public int getAttemptedQty() { return attemptedQty; }
    }

    // Checked Exception for Payment
    static class PaymentFailedException extends Exception {
        public PaymentFailedException(String msg) {
            super(msg);
        }
    }

    // Wrapper Exception for Chaining
    static class OrderProcessingException extends Exception {
        private final String orderId;

        public OrderProcessingException(String orderId, String msg, Throwable cause) {
            super(msg, cause);
            this.orderId = orderId;
        }

        public String getOrderId() { return orderId; }
    }

    static class OrderProcessor {
        private final Map<String, Integer> stock = new HashMap<>();

        public OrderProcessor() {
            stock.put("SHIRT", 10);
            stock.put("SUIT", 3);
        }

        public void process(String orderId, String item, int qty, double paid, double price)
                throws OrderProcessingException {
            System.out.printf("%n[AUDIT START] Processing Order %s: %d x %s...%n", orderId, qty, item);
            boolean success = false;
            try {
                if (qty <= 0) {
                    throw new InvalidQuantityException("Quantity must be greater than zero! Given: " + qty, qty);
                }

                int available = stock.getOrDefault(item, 0);
                if (qty > available) {
                    throw new InsufficientStockException("Insufficient stock! Available: " + available + ", Requested: " + qty,
                            available, qty);
                }

                double required = qty * price;
                if (paid < required) {
                    throw new PaymentFailedException(String.format("Payment shortfall: Needed ₹%.2f, Paid ₹%.2f", required, paid));
                }

                stock.put(item, available - qty);
                System.out.printf(">> Success: Order %s completed. Remaining %s stock: %d%n",
                        orderId, item, stock.get(item));
                success = true;

            } catch (InsufficientStockException | PaymentFailedException e) {
                System.err.println(">> [Multi-catch intercepted] " + e.getClass().getSimpleName() + ": " + e.getMessage());
                // Chain root cause
                throw new OrderProcessingException(orderId, "Processing pipeline failed", e);
            } finally {
                System.out.printf("[AUDIT FINALLY] Order %s finished with status: %s.%n",
                        orderId, (success ? "COMMITTED" : "ABORTED"));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     DAY 06: EXCEPTION HANDLING STANDALONE        ");
        System.out.println("==================================================");

        OrderProcessor processor = new OrderProcessor();

        // 1. Valid execution
        try {
            processor.process("ORD-101", "SHIRT", 2, 1000.0, 500.0);
        } catch (OrderProcessingException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 2. Unchecked Exception
        try {
            processor.process("ORD-102", "SHIRT", -5, 1000.0, 500.0);
        } catch (InvalidQuantityException e) {
            System.err.println("Caught Unchecked InvalidQuantityException: " + e.getMessage());
            System.out.println(">> Handled gracefully without crash.");
        } catch (OrderProcessingException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 3. Checked Exception Chained (Insufficient Stock)
        try {
            processor.process("ORD-103", "SUIT", 15, 30000.0, 2000.0);
        } catch (OrderProcessingException e) {
            System.err.println("Caught Chained OrderProcessingException: " + e.getMessage());
            System.err.println("   Root Cause: " + e.getCause().getClass().getSimpleName() + " -> " + e.getCause().getMessage());
            System.out.println(">> Handled gracefully without crash.");
        }

        // 4. Checked Exception Chained (Payment Failure)
        try {
            processor.process("ORD-104", "SUIT", 1, 500.0, 2000.0);
        } catch (OrderProcessingException e) {
            System.err.println("Caught Chained OrderProcessingException: " + e.getMessage());
            System.err.println("   Root Cause: " + e.getCause().getClass().getSimpleName() + " -> " + e.getCause().getMessage());
            System.out.println(">> Handled gracefully without crash.");
        }

        System.out.println("\nAll test cases executed and verified!");
    }
}
