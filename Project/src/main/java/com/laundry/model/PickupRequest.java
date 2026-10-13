package com.laundry.model;

import com.laundry.strategy.SurchargeStrategy;
import java.time.LocalDateTime;

/**
 * PickupRequest
 * 
 * Represents a customer's scheduled pickup request.
 * Enqueued into the logistics Queue (ArrayDeque / FIFO).
 */
public class PickupRequest extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Customer customer;
    private String address;
    private String timeSlot;
    private SurchargeStrategy strategy;
    private boolean isPickedUp;
    private LocalDateTime scheduledAt;

    public PickupRequest() {
        super();
        this.isPickedUp = false;
        this.scheduledAt = LocalDateTime.now();
    }

    public PickupRequest(String id, Customer customer, String address, String timeSlot, SurchargeStrategy strategy) {
        super(id);
        this.customer = customer;
        this.address = address;
        this.timeSlot = timeSlot;
        this.strategy = strategy;
        this.isPickedUp = false;
        this.scheduledAt = LocalDateTime.now();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public SurchargeStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SurchargeStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    @Override
    public String toString() {
        return String.format("[%s] Customer: %-15s | Slot: %-22s | Speed: %-8s | Status: %s",
                id,
                (customer != null ? customer.getName() : "N/A"),
                timeSlot,
                (strategy != null ? strategy.getStrategyName() : "STANDARD"),
                (isPickedUp ? "COMPLETED" : "PENDING"));
    }
}
