package com.laundry.strategy;

/**
 * StandardSurchargeStrategy
 * Standard delivery: zero surcharge (0%).
 */
public class StandardSurchargeStrategy implements SurchargeStrategy {

    @Override
    public double calculateSurcharge(double subtotal) {
        return 0.0;
    }

    @Override
    public String getStrategyName() {
        return "Standard Delivery (0% Surcharge, 48-Hour Turnaround)";
    }
}
