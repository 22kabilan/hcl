package com.payment;

import java.time.LocalDateTime;

/**
 * Abstract Payment
 * Demonstrates:
 * - Abstract class with abstract and concrete methods
 * - Method overloading (compile-time polymorphism)
 * - Base state encapsulation (protected fields)
 */
public abstract class Payment {

    protected final String transactionId;
    protected double amount;
    protected String status; // "PENDING", "COMPLETED", "FAILED", "REFUNDED"
    protected final LocalDateTime timestamp;

    public Payment(String transactionId, double amount) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be empty.");
        }
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }
        this.transactionId = transactionId.trim();
        this.amount = amount;
        this.status = "PENDING";
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Abstract method: must be overridden by specific payment channels
    public abstract boolean processPayment();

    // Overloaded Method 1: standard pay()
    public boolean pay() {
        System.out.printf("[%s] Initiating base payment of ₹%.2f...%n", transactionId, amount);
        return processPayment();
    }

    // Overloaded Method 2: pay with custom transaction note
    public boolean pay(String note) {
        System.out.printf("[%s] Initiating payment of ₹%.2f with note: '%s'...%n", transactionId, amount, note);
        return processPayment();
    }

    // Overloaded Method 3: pay with promo discount
    public boolean pay(double discountPercentage, String promoCode) {
        if (discountPercentage > 0 && discountPercentage <= 50) {
            double discountAmount = amount * (discountPercentage / 100.0);
            double finalAmount = amount - discountAmount;
            System.out.printf("[%s] Applied promo '%s' (%.1f%% off). Discount: ₹%.2f, Payable: ₹%.2f%n",
                    transactionId, promoCode, discountPercentage, discountAmount, finalAmount);
            this.amount = finalAmount;
        } else {
            System.out.printf("[%s] Invalid or expired promo '%s'. Proceeding with standard amount ₹%.2f%n",
                    transactionId, promoCode, amount);
        }
        return processPayment();
    }

    public void printReceipt() {
        System.out.println("==================================================");
        System.out.println("                PAYMENT RECEIPT                   ");
        System.out.println("==================================================");
        System.out.println("Transaction ID : " + transactionId);
        System.out.printf("Total Amount   : ₹%.2f%n", amount);
        System.out.println("Status         : " + status);
        System.out.println("Date & Time    : " + timestamp);
        System.out.println("==================================================");
    }
}
