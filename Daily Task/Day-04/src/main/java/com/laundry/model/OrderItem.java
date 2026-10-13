package com.laundry.model;

import java.util.Objects;

/**
 * OrderItem Model (Day 04 Project Task)
 * Represents an individual garment item in a laundry order.
 */
public class OrderItem {

    private final String itemId;
    private String itemType;       // e.g. "SHIRT", "TROUSER", "SUIT", "BEDSHEET"
    private String serviceType;    // e.g. "WASH_AND_FOLD", "DRY_CLEAN", "STEAM_PRESS"
    private double unitPrice;
    private int quantity;
    private String tagNumber;      // e.g. "TAG-1001"

    public OrderItem(String itemId, String itemType, String serviceType, double unitPrice, int quantity, String tagNumber) {
        if (itemId == null || itemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (unitPrice < 0.0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }

        this.itemId = itemId.trim();
        this.itemType = itemType;
        this.serviceType = serviceType;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.tagNumber = tagNumber;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice >= 0.0) {
            this.unitPrice = unitPrice;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public double calculateSubtotal() {
        return unitPrice * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(itemId, orderItem.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public String toString() {
        return String.format("OrderItem[ID=%s, Tag=%s, Type='%s', Service='%s', Qty=%d, Subtotal=₹%.2f]",
                itemId, tagNumber, itemType, serviceType, quantity, calculateSubtotal());
    }
}
