package com.laundry.model;

/**
 * DeliveryAgent role entity
 */
public class DeliveryAgent extends User {

    private String vehicleNumber;

    public DeliveryAgent(String id, String fullName, String email, String phone, String vehicleNumber) {
        super(id, fullName, email, phone, "DELIVERY_AGENT");
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Override
    public String getRolePermissionSummary() {
        return "Pick up laundry orders from customer premises and deliver processed orders.";
    }
}
