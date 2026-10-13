package com.laundry.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * OrderItem
 * 
 * Represents an individual garment line within a laundry order.
 * Captures historical unit price to preserve non-retroactivity (NFR-P4)
 * and maintains unique tags per physical garment piece.
 * One of the 3 primary domain entities (Day 04 OOP Concepts).
 */
public class OrderItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String itemType;
    private String serviceName;
    private int quantity;
    private double unitPrice; // Fixed historical price at booking time
    private List<String> garmentTags; // Individual tags per physical item

    public OrderItem() {
        super();
        this.garmentTags = new ArrayList<>();
    }

    public OrderItem(String id, String itemType, String serviceName, int quantity, double unitPrice) {
        super(id);
        this.itemType = itemType;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.garmentTags = new ArrayList<>();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<String> getGarmentTags() {
        return Collections.unmodifiableList(garmentTags);
    }

    public void addGarmentTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            this.garmentTags.add(tag.trim());
        }
    }

    public double getSubtotal() {
        return this.quantity * this.unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(itemType, orderItem.itemType) &&
                Objects.equals(serviceName, orderItem.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), itemType, serviceName);
    }

    @Override
    public String toString() {
        return String.format("%-15s [%-12s] x%d @ %.2f = %.2f INR | Tags: %s",
                itemType, serviceName, quantity, unitPrice, getSubtotal(), garmentTags);
    }
}
