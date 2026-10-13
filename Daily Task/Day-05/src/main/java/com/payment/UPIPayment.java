package com.payment;

/**
 * UPIPayment
 * Extends Payment and implements Refundable.
 */
public class UPIPayment extends Payment implements Refundable {

    private final String upiId;
    private final String referenceNumber;
    private double refundedAmount;

    public UPIPayment(String transactionId, double amount, String upiId, String referenceNumber) {
        super(transactionId, amount);
        this.upiId = upiId;
        this.referenceNumber = referenceNumber;
        this.refundedAmount = 0.0;
    }

    public String getUpiId() {
        return upiId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    @Override
    public boolean processPayment() {
        System.out.printf(">> Sending UPI Collect Request to %s (Ref: %s)...%n", upiId, referenceNumber);
        System.out.println(">> UPI payment verified instantly via NPCI gateway!");
        this.status = "COMPLETED";
        return true;
    }

    @Override
    public boolean processRefund(double refundAmount, String reason) {
        if (refundAmount <= 0 || (refundedAmount + refundAmount) > amount) {
            System.out.println(">> [UPI Refund Failed] Invalid refund amount: ₹" + refundAmount);
            return false;
        }
        refundedAmount += refundAmount;
        if (refundedAmount == amount) {
            this.status = "REFUNDED";
        }
        System.out.printf(">> [UPI Refund Processed] ₹%.2f returned to VPA %s. Reason: %s.%n",
                refundAmount, upiId, reason);
        return true;
    }

    @Override
    public double getRefundableAmount() {
        return amount - refundedAmount;
    }
}
