package com.laundry.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * LaundryOrder Model (Day 04 Project Task)
 * Encapsulates order identity, items, order stage, and price computation.
 */
public class LaundryOrder {

    private final String orderId;
    private final String customerId;
    private final List<OrderItem> items;
    private String orderStatus;    // e.g. "RECEIVED", "IN_PROCESS", "READY", "DELIVERED"
    private boolean isExpress;
    private double expressSurchargeRate; // e.g. 0.40 for 40% express surcharge

    public LaundryOrder(String orderId, String customerId, boolean isExpress) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty.");
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty.");
        }

        this.orderId = orderId.trim();
        this.customerId = customerId.trim();
        this.isExpress = isExpress;
        this.orderStatus = "RECEIVED";
        this.items = new ArrayList<>();
        this.expressSurchargeRate = 0.40; // Default 40%
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isExpress() {
        return isExpress;
    }

    public void setExpress(boolean express) {
        isExpress = express;
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (OrderItem item : items) {
            subtotal += item.calculateSubtotal();
        }
        return subtotal;
    }

    public double calculateSurcharge() {
        if (isExpress) {
            return calculateSubtotal() * expressSurchargeRate;
        }
        return 0.0;
    }

    public double calculateTotalAmount() {
        return calculateSubtotal() + calculateSurcharge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaundryOrder that = (LaundryOrder) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return String.format("LaundryOrder[ID=%s, Customer=%s, Status='%s', Express=%b, Items=%d, Total=₹%.2f]",
                orderId, customerId, orderStatus, isExpress, items.size(), calculateTotalAmount());
    }
}
