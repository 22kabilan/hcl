package com.laundry.model;

/**
 * Customer role entity extending User
 */
public class Customer extends User {

    private String deliveryAddress;
    private int loyaltyPoints;

    public Customer(String id, String fullName, String email, String phone, String deliveryAddress) {
        super(id, fullName, email, phone, "CUSTOMER");
        this.deliveryAddress = deliveryAddress;
        this.loyaltyPoints = 0;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        if (points > 0) {
            this.loyaltyPoints += points;
        }
    }

    @Override
    public String getRolePermissionSummary() {
        return "Schedule pickups, track live order status, and review invoices.";
    }
}
