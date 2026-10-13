package com.laundry.strategy;

/**
 * SurchargeStrategy
 * 
 * Strategy interface for dynamic order surcharge calculation (e.g., Standard vs Express delivery).
 * Developed as part of Day 05 Inheritance & Polymorphism.
 */
public interface SurchargeStrategy {

    /**
     * Calculates the surcharge amount based on the order's subtotal.
     * 
     * @param subtotal base sum of order items
     * @return calculated surcharge fee
     */
    double calculateSurcharge(double subtotal);

    /**
     * @return human-readable strategy name
     */
    String getStrategyName();

    /**
     * @return promised turnaround delivery time in days
     */
    int getDeliveryTurnaroundDays();
}
