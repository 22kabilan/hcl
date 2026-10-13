import java.time.LocalDateTime;

/**
 * PaymentSystemApp (Day 05 Single-File Standalone Runner)
 * Demonstrates:
 * - Abstract class Payment with abstract processPayment()
 * - Overloaded pay() methods (compile-time polymorphism)
 * - CardPayment, UPIPayment, CashPayment subclasses (runtime polymorphism)
 * - Refundable interface implementation
 */
public class PaymentSystemApp {

    interface Refundable {
        boolean processRefund(double amount, String reason);
        double getRefundableAmount();
    }

    static abstract class Payment {
        protected final String transactionId;
        protected double amount;
        protected String status;
        protected final LocalDateTime timestamp;

        public Payment(String transactionId, double amount) {
            this.transactionId = transactionId;
            this.amount = amount;
            this.status = "PENDING";
            this.timestamp = LocalDateTime.now();
        }

        public abstract boolean processPayment();

        public boolean pay() {
            System.out.printf("[%s] Standard pay for ₹%.2f%n", transactionId, amount);
            return processPayment();
        }

        public boolean pay(String note) {
            System.out.printf("[%s] Pay ₹%.2f with note: '%s'%n", transactionId, amount, note);
            return processPayment();
        }

        public boolean pay(double discountPercentage, String promoCode) {
            double discount = amount * (discountPercentage / 100.0);
            this.amount -= discount;
            System.out.printf("[%s] Promo '%s' applied (-%.0f%%). Payable: ₹%.2f%n",
                    transactionId, promoCode, discountPercentage, this.amount);
            return processPayment();
        }

        public void printReceipt() {
            System.out.println("Receipt: " + transactionId + " | Status: " + status + " | Amount: ₹" + amount);
        }
    }

    static class CardPayment extends Payment implements Refundable {
        private final String cardNumber;
        private double refunded;

        public CardPayment(String id, double amount, String cardNum) {
            super(id, amount);
            this.cardNumber = cardNum;
        }

        @Override
        public boolean processPayment() {
            System.out.println(">> Card authorized (" + cardNumber + ")");
            this.status = "COMPLETED";
            return true;
        }

        @Override
        public boolean processRefund(double refundAmount, String reason) {
            this.refunded += refundAmount;
            this.status = "REFUNDED";
            System.out.printf(">> Card Refund: ₹%.2f credited. Reason: %s%n", refundAmount, reason);
            return true;
        }

        @Override
        public double getRefundableAmount() {
            return amount - refunded;
        }
    }

    static class UPIPayment extends Payment implements Refundable {
        private final String vpa;
        private double refunded;

        public UPIPayment(String id, double amount, String vpa) {
            super(id, amount);
            this.vpa = vpa;
        }

        @Override
        public boolean processPayment() {
            System.out.println(">> UPI approved via VPA (" + vpa + ")");
            this.status = "COMPLETED";
            return true;
        }

        @Override
        public boolean processRefund(double refundAmount, String reason) {
            this.refunded += refundAmount;
            this.status = "REFUNDED";
            System.out.printf(">> UPI Refund: ₹%.2f credited to %s. Reason: %s%n", refundAmount, vpa, reason);
            return true;
        }

        @Override
        public double getRefundableAmount() {
            return amount - refunded;
        }
    }

    static class CashPayment extends Payment {
        public CashPayment(String id, double amount) {
            super(id, amount);
        }

        @Override
        public boolean processPayment() {
            System.out.println(">> Cash received at counter");
            this.status = "COMPLETED";
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       DAY 05: PAYMENT HIERARCHY SYSTEM           ");
        System.out.println("==================================================");

        Payment[] payments = new Payment[]{
                new CardPayment("TXN-301", 2400.0, "XXXX-XXXX-XXXX-1234"),
                new UPIPayment("TXN-302", 750.0, "alice@upi"),
                new CashPayment("TXN-303", 400.0)
        };

        System.out.println("\n--- Polymorphic Execution & Overloading ---");
        payments[0].pay();
        payments[1].pay("Urgent laundry wash");
        payments[2].pay(10.0, "FESTIVE10");

        System.out.println("\n--- Receipt Summary ---");
        for (Payment p : payments) {
            p.printReceipt();
        }

        System.out.println("\n--- Interface-Driven Refunds ---");
        for (Payment p : payments) {
            if (p instanceof Refundable r) {
                r.processRefund(200.0, "Damage compensation");
                System.out.printf("Remaining balance for %s: ₹%.2f%n", p.transactionId, r.getRefundableAmount());
            } else {
                System.out.println(p.transactionId + " (Cash) cannot be refunded via digital gateway.");
            }
        }
    }
}
