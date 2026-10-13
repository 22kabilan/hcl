package com.laundry.strategy;

/**
 * ExpressSurchargeStrategy
 * Express same-day turnaround: 40% surcharge.
 */
public class ExpressSurchargeStrategy implements SurchargeStrategy {

    private final double surchargeRate;

    public ExpressSurchargeStrategy() {
        this(0.40); // 40% default express rate
    }

    public ExpressSurchargeStrategy(double surchargeRate) {
        this.surchargeRate = surchargeRate;
    }

    @Override
    public double calculateSurcharge(double subtotal) {
        return subtotal * surchargeRate;
    }

    @Override
    public String getStrategyName() {
        return String.format("Express Priority (%.0f%% Surcharge, Same-Day Turnaround)", surchargeRate * 100);
    }
}
