package com.payment;

/**
 * Refundable Interface
 * Defines capability for payment mechanisms supporting automated refund workflows.
 */
public interface Refundable {

    /**
     * Issues a refund against the payment.
     * @param amount Amount to refund.
     * @param reason Business reason for refund.
     * @return true if refund successfully processed.
     */
    boolean processRefund(double amount, String reason);

    /**
     * Returns the remaining refundable balance.
     */
    double getRefundableAmount();
}
