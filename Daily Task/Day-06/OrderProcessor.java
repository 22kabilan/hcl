// Custom Checked Exception
class InsufficientStockException extends Exception {
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Custom Unchecked Exception
class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

public class OrderProcessor {

    static int stock = 10;

    // method demonstrating throw, throws, and exception chaining
    public static void processOrder(int qty) throws InsufficientStockException {
        System.out.println("\nProcessing order for quantity: " + qty);

        try {
            // Unchecked exception check
            if (qty <= 0) {
                throw new InvalidQuantityException("Quantity must be greater than 0! Given: " + qty);
            }

            // Checked exception check with chaining
            if (qty > stock) {
                Exception reason = new Exception("Warehouse only has " + stock + " items in stock.");
                throw new InsufficientStockException("Cannot place order due to low stock.", reason);
            }

            stock -= qty;
            System.out.println("Order successful! Remaining stock: " + stock);

        } finally {
            // finally block always runs
            System.out.println("[Audit] Transaction checked at system timestamp.");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Order Processor Demo ---");

        // Test 1: Normal valid order
        try {
            processOrder(3);
        } catch (InsufficientStockException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test 2: Unchecked exception (invalid quantity)
        try {
            processOrder(-2);
        } catch (InvalidQuantityException e) {
            System.out.println("Caught Unchecked: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("Caught Checked: " + e.getMessage());
        }

        // Test 3: Checked exception with cause preserved (insufficient stock)
        try {
            processOrder(15);
        } catch (InsufficientStockException e) {
            System.out.println("Caught Checked: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Cause: " + e.getCause().getMessage());
            }
        }

        System.out.println("\nProgram finished without crashing!");
    }
}
