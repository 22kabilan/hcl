package com.laundry.exception;

import com.laundry.model.OrderStage;

/**
 * InvalidStageTransitionException
 * 
 * Thrown when an order stage transition violates the strict laundry workflow:
 * RECEIVED -> WASHING -> IRONING -> PACKING -> READY_FOR_DELIVERY -> DELIVERED.
 * Enforces Business Rule 1 (NFR-P2).
 */
public class InvalidStageTransitionException extends LaundryServiceException {
    private static final long serialVersionUID = 1L;

    private final OrderStage currentStage;
    private final OrderStage attemptedStage;

    public InvalidStageTransitionException(String orderId, OrderStage currentStage, OrderStage attemptedStage) {
        super(String.format("Invalid stage transition for Order [%s]: cannot move from [%s] to [%s]. Stages must follow: RECEIVED -> WASHING -> IRONING -> PACKING -> READY_FOR_DELIVERY -> DELIVERED.",
                orderId, currentStage, attemptedStage));
        this.currentStage = currentStage;
        this.attemptedStage = attemptedStage;
    }

    public OrderStage getCurrentStage() {
        return currentStage;
    }

    public OrderStage getAttemptedStage() {
        return attemptedStage;
    }
}
