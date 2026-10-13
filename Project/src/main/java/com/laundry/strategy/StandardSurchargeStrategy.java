package com.laundry.strategy;

import com.laundry.constants.LaundryConstants;

/**
 * StandardSurchargeStrategy
 * 
 * Standard delivery option: 0% surcharge, delivered within standard SLA (3 days).
 */
public class StandardSurchargeStrategy implements SurchargeStrategy {

    @Override
    public double calculateSurcharge(double subtotal) {
        return subtotal * LaundryConstants.STANDARD_SURCHARGE_RATE; // 0.00
    }

    @Override
    public String getStrategyName() {
        return "STANDARD";
    }

    @Override
    public int getDeliveryTurnaroundDays() {
        return LaundryConstants.STANDARD_DELIVERY_DAYS;
    }

    @Override
    public String toString() {
        return "Standard Delivery (0% Surcharge, 3-Day SLA)";
    }
}
