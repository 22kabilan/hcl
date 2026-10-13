package com.laundry.model;

/**
 * OrderStage
 * 
 * Strict lifecycle stages of a laundry order.
 * Validates transitions to satisfy NFR-P2.
 */
public enum OrderStage {
    RECEIVED("Received at workshop"),
    WASHING("Garment undergoing washing/cleaning"),
    IRONING("Garment undergoing steam pressing/ironing"),
    PACKING("Garments folded and packaged with quality check"),
    READY_FOR_DELIVERY("Packed and assigned for dispatch"),
    DELIVERED("Handed over to customer");

    private final String description;

    OrderStage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Determines whether transitioning from this stage to nextStage is valid.
     * Enforces strict chronological workflow without skipping or reversing.
     */
    public boolean canTransitionTo(OrderStage nextStage) {
        if (nextStage == null) return false;
        return nextStage.ordinal() == this.ordinal() + 1;
    }
}
