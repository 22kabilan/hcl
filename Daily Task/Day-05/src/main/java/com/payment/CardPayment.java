package com.payment;

/**
 * CardPayment
 * Extends Payment and implements Refundable.
 */
public class CardPayment extends Payment implements Refundable {

    private final String cardNumber; // Masked e.g. "XXXXXXXXXXXX1234"
    private final String cardHolderName;
    private final String cardType;   // "CREDIT" or "DEBIT"
    private double refundedAmount;

    public CardPayment(String transactionId, double amount, String cardNumber, String cardHolderName, String cardType) {
        super(transactionId, amount);
        this.cardNumber = maskCardNumber(cardNumber);
        this.cardHolderName = cardHolderName;
        this.cardType = cardType.toUpperCase();
        this.refundedAmount = 0.0;
    }

    private String maskCardNumber(String raw) {
        if (raw == null || raw.length() < 4) return "XXXX-XXXX-XXXX-0000";
        String clean = raw.replaceAll("\\s+", "");
        String lastFour = clean.substring(clean.length() - 4);
        return "XXXX-XXXX-XXXX-" + lastFour;
    }

    @Override
    public boolean processPayment() {
        System.out.printf(">> Processing %s Card payment via Gateway for %s (%s)...%n",
                cardType, cardHolderName, cardNumber);
        this.status = "COMPLETED";
        System.out.println(">> Card authorized successfully! Transaction: " + transactionId);
        return true;
    }

    @Override
    public boolean processRefund(double refundAmount, String reason) {
        if (refundAmount <= 0 || (refundedAmount + refundAmount) > amount) {
            System.out.println(">> [Refund Failed] Invalid refund amount: ₹" + refundAmount);
            return false;
        }
        refundedAmount += refundAmount;
        if (refundedAmount == amount) {
            this.status = "REFUNDED";
        }
        System.out.printf(">> [Refund Approved] ₹%.2f credited to Card %s. Reason: %s.%n",
                refundAmount, cardNumber, reason);
        return true;
    }

    @Override
    public double getRefundableAmount() {
        return amount - refundedAmount;
    }
}
