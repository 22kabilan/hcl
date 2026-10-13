package com.laundry.strategy;

import com.laundry.constants.LaundryConstants;

/**
 * ExpressSurchargeStrategy
 * 
 * Express delivery option: 50% surcharge, expedited same-day / next-day SLA (1 day).
 */
public class ExpressSurchargeStrategy implements SurchargeStrategy {

    @Override
    public double calculateSurcharge(double subtotal) {
        return subtotal * LaundryConstants.EXPRESS_SURCHARGE_RATE; // 50% extra
    }

    @Override
    public String getStrategyName() {
        return "EXPRESS";
    }

    @Override
    public int getDeliveryTurnaroundDays() {
        return LaundryConstants.EXPRESS_DELIVERY_DAYS;
    }

    @Override
    public String toString() {
        return "Express Delivery (+50% Surcharge, 24-Hour SLA)";
    }
}
