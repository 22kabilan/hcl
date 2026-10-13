package com.laundry.strategy;

/**
 * SurchargeStrategy Interface (Day 05 Project Task)
 * Implements Strategy pattern for billing surcharges.
 */
public interface SurchargeStrategy {

    /**
     * Calculates the surcharge amount based on base subtotal.
     */
    double calculateSurcharge(double subtotal);

    /**
     * Strategy human-readable descriptor.
     */
    String getStrategyName();
}
