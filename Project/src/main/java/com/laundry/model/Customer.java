package com.laundry.model;

import com.laundry.constants.LaundryConstants;
import java.util.Objects;

/**
 * Customer
 * 
 * Represents a client placing laundry orders and requesting pickups.
 * One of the 3 primary domain entities (Day 04 OOP Concepts).
 */
public class Customer extends User {
    private static final long serialVersionUID = 1L;

    private String address;
    private String preferredSlot;
    private int totalOrdersPlaced;

    public Customer() {
        super();
        this.role = LaundryConstants.ROLE_CUSTOMER;
    }

    public Customer(String id, String name, String phone, String address) {
        this(id, name, phone, name.toLowerCase().replace(" ", "") + "@example.com", address, "Morning");
    }

    public Customer(String id, String name, String phone, String email, String address, String preferredSlot) {
        super(id, name, phone, email, LaundryConstants.ROLE_CUSTOMER);
        this.address = address;
        this.preferredSlot = preferredSlot;
        this.totalOrdersPlaced = 0;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreferredSlot() {
        return preferredSlot;
    }

    public void setPreferredSlot(String preferredSlot) {
        this.preferredSlot = preferredSlot;
    }

    public int getTotalOrdersPlaced() {
        return totalOrdersPlaced;
    }

    public void incrementOrdersCount() {
        this.totalOrdersPlaced++;
    }

    @Override
    public String getRoleDescription() {
        return "Customer placing orders, requesting pickups, tracking stage and paying bills.";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", ordersCount=" + totalOrdersPlaced +
                '}';
    }
}
