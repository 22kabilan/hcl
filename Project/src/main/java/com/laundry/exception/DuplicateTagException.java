package com.laundry.exception;

/**
 * DuplicateTagException
 * 
 * Thrown when an item tag is not globally unique.
 * Enforces Business Rule 2 (NFR-P1).
 */
public class DuplicateTagException extends LaundryServiceException {
    private static final long serialVersionUID = 1L;

    private final String attemptedTag;

    public DuplicateTagException(String attemptedTag) {
        super(String.format("Duplicate garment tag rejected: [%s] already exists in the system. Garment tags must be globally unique.", attemptedTag));
        this.attemptedTag = attemptedTag;
    }

    public String getAttemptedTag() {
        return attemptedTag;
    }
}
