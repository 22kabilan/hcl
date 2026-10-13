package com.payment;

/**
 * CashPayment
 * Extends Payment (Direct offline settlement; not refundable via digital gateway).
 */
public class CashPayment extends Payment {

    private double cashTendered;
    private double changeReturned;

    public CashPayment(String transactionId, double amount, double cashTendered) {
        super(transactionId, amount);
        this.cashTendered = cashTendered;
        this.changeReturned = 0.0;
    }

    public double getCashTendered() {
        return cashTendered;
    }

    public double getChangeReturned() {
        return changeReturned;
    }

    @Override
    public boolean processPayment() {
        if (cashTendered < amount) {
            System.out.printf(">> [Cash Error] Insufficient cash tendered! Required: ₹%.2f, Given: ₹%.2f%n",
                    amount, cashTendered);
            this.status = "FAILED";
            return false;
        }

        this.changeReturned = cashTendered - amount;
        this.status = "COMPLETED";
        System.out.printf(">> Cash received: ₹%.2f. Change returned to customer: ₹%.2f.%n",
                cashTendered, changeReturned);
        return true;
    }
}
