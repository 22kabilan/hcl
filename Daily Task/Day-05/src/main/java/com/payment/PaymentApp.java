package com.payment;

/**
 * PaymentApp
 * Demonstrates:
 * - Runtime Polymorphism (handling payments through base abstract Payment reference)
 * - Method Overloading (compile-time polymorphism)
 * - Interface-based programming (processing refunds via Refundable references)
 */
public class PaymentApp {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     DAY 05: INHERITANCE & POLYMORPHISM DEMO      ");
        System.out.println("==================================================");

        // 1. Polymorphic Array of Payment objects
        Payment[] payments = new Payment[]{
                new CardPayment("TXN-101", 3500.00, "4111222233334444", "Rahul Sharma", "CREDIT"),
                new UPIPayment("TXN-102", 1250.00, "rahul@okaxis", "UPI-REF-998877"),
                new CashPayment("TXN-103", 800.00, 1000.00)
        };

        // 2. Overloaded pay() demonstrations
        System.out.println("\n--- Step 1: Processing Payments via Polymorphism ---");
        // standard pay()
        payments[0].pay();
        // pay with note
        payments[1].pay("Express Laundry Delivery Payment");
        // pay with promo
        payments[2].pay(10.0, "WELCOME10");

        // 3. Printing Receipts
        System.out.println("\n--- Step 2: Payment Receipts ---");
        for (Payment p : payments) {
            p.printReceipt();
        }

        // 4. Interface programming with Refundable
        System.out.println("--- Step 3: Interface-Driven Refund Processing ---");
        for (Payment p : payments) {
            if (p instanceof Refundable refundable) {
                System.out.println("Payment " + p.getTransactionId() + " implements Refundable. Issuing partial refund...");
                refundable.processRefund(500.00, "Customer cancelled 1 dry-cleaned suit");
                System.out.printf("Remaining refundable balance: ₹%.2f%n%n", refundable.getRefundableAmount());
            } else {
                System.out.println("Payment " + p.getTransactionId() + " (" + p.getClass().getSimpleName() + ") does NOT implement Refundable.%n");
            }
        }

        System.out.println("==================================================");
        System.out.println("        PAYMENT SYSTEM DEMO COMPLETED             ");
        System.out.println("==================================================");
    }
}
