package com.laundry.model;

import com.laundry.constants.LaundryConstants;
import com.laundry.strategy.StandardSurchargeStrategy;
import com.laundry.strategy.SurchargeStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * LaundryOrder
 * 
 * Central aggregate entity representing a laundry processing order.
 * Manages item lines, processing stage progression, and strategy-driven financial computation.
 * One of the 3 primary domain entities (Day 04 OOP Concepts).
 */
public class LaundryOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Customer customer;
    private List<OrderItem> items;
    private SurchargeStrategy surchargeStrategy;
    private OrderStage currentStage;
    private String pickupAddress;
    private String preferredSlot;
    private LocalDateTime deliveryDueDate;
    private boolean isPaid;
    private List<String> stageHistoryLogs;

    public LaundryOrder() {
        super();
        this.items = new ArrayList<>();
        this.surchargeStrategy = new StandardSurchargeStrategy();
        this.currentStage = OrderStage.RECEIVED;
        this.isPaid = false;
        this.stageHistoryLogs = new ArrayList<>();
        recordStageLog(OrderStage.RECEIVED, "Order initialized in system");
    }

    public LaundryOrder(String id, Customer customer, SurchargeStrategy strategy, String pickupAddress, String preferredSlot) {
        super(id);
        this.customer = customer;
        this.items = new ArrayList<>();
        this.surchargeStrategy = (strategy != null) ? strategy : new StandardSurchargeStrategy();
        this.currentStage = OrderStage.RECEIVED;
        this.pickupAddress = pickupAddress;
        this.preferredSlot = preferredSlot;
        this.isPaid = false;
        this.deliveryDueDate = LocalDateTime.now().plusDays(this.surchargeStrategy.getDeliveryTurnaroundDays());
        this.stageHistoryLogs = new ArrayList<>();
        recordStageLog(OrderStage.RECEIVED, "Order created with " + this.surchargeStrategy.getStrategyName() + " turnaround");
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            this.items.add(item);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public SurchargeStrategy getSurchargeStrategy() {
        return surchargeStrategy;
    }

    public void setSurchargeStrategy(SurchargeStrategy surchargeStrategy) {
        this.surchargeStrategy = surchargeStrategy;
        this.deliveryDueDate = LocalDateTime.now().plusDays(this.surchargeStrategy.getDeliveryTurnaroundDays());
    }

    public OrderStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(OrderStage currentStage) {
        this.currentStage = currentStage;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPreferredSlot() {
        return preferredSlot;
    }

    public void setPreferredSlot(String preferredSlot) {
        this.preferredSlot = preferredSlot;
    }

    public LocalDateTime getDeliveryDueDate() {
        return deliveryDueDate;
    }

    public void setDeliveryDueDate(LocalDateTime deliveryDueDate) {
        this.deliveryDueDate = deliveryDueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public List<String> getStageHistoryLogs() {
        return Collections.unmodifiableList(stageHistoryLogs);
    }

    public void recordStageLog(OrderStage stage, String note) {
        String logEntry = String.format("[%s] -> %s (%s)", LocalDateTime.now().toString(), stage.name(), note);
        this.stageHistoryLogs.add(logEntry);
    }

    public int getTotalGarmentsCount() {
        int count = 0;
        for (OrderItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    // ==========================================
    // FINANCIAL COMPUTATIONS (FR4)
    // ==========================================
    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (OrderItem item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    public double calculateSurcharge() {
        return surchargeStrategy.calculateSurcharge(calculateSubtotal());
    }

    public double calculateTax() {
        double taxableAmount = calculateSubtotal() + calculateSurcharge();
        return taxableAmount * LaundryConstants.GST_TAX_RATE;
    }

    public double calculateTotalBill() {
        return calculateSubtotal() + calculateSurcharge() + calculateTax();
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        LaundryOrder that = (LaundryOrder) o;
        return Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customer);
    }

    @Override
    public String toString() {
        return String.format("Order #%s | Customer: %s | Stage: %s | Items: %d | Total: %.2f INR (%s)",
                id,
                (customer != null ? customer.getName() : "Unknown"),
                currentStage.name(),
                getTotalGarmentsCount(),
                calculateTotalBill(),
                surchargeStrategy.getStrategyName());
    }
}
