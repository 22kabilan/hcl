package com.laundry.exception;

/**
 * InvalidStageTransitionException (Day 06 Project Task)
 * Enforces business rule NFR-P2: Status transitions must be strictly validated.
 * (e.g. Cannot jump from RECEIVED directly to DELIVERED without passing through WASHING, IRONING, PACKED).
 */
public class InvalidStageTransitionException extends LaundryServiceException {

    private final String currentStage;
    private final String attemptedStage;

    public InvalidStageTransitionException(String currentStage, String attemptedStage) {
        super(String.format("Illegal stage progression: Cannot transition order from [%s] to [%s].",
                currentStage, attemptedStage));
        this.currentStage = currentStage;
        this.attemptedStage = attemptedStage;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public String getAttemptedStage() {
        return attemptedStage;
    }
}
