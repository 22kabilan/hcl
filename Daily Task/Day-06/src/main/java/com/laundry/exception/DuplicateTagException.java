package com.laundry.exception;

/**
 * DuplicateTagException (Day 06 Project Task)
 * Enforces business rule NFR-P1: RFID/Barcode garment tags must be strictly unique per item.
 */
public class DuplicateTagException extends LaundryServiceException {

    private final String tagNumber;

    public DuplicateTagException(String tagNumber) {
        super("Business Rule Violation: Garment tag '" + tagNumber + "' is already assigned to another item.");
        this.tagNumber = tagNumber;
    }

    public String getTagNumber() {
        return tagNumber;
    }
}
