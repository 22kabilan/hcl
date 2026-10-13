package com.laundry.model;

import java.util.Objects;

/**
 * ServiceItem
 * 
 * Represents a service offering and item price line in the catalog (e.g., Wash & Iron Shirt @ 50 INR).
 */
public class ServiceItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String serviceName;
    private String itemType;
    private double unitPrice;

    public ServiceItem() {
        super();
    }

    public ServiceItem(String id, String serviceName, String itemType, double unitPrice) {
        super(id);
        this.serviceName = serviceName;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        ServiceItem that = (ServiceItem) o;
        return Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(itemType, that.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), serviceName, itemType);
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s - %-18s : %.2f INR", id, serviceName, itemType, unitPrice);
    }
}
