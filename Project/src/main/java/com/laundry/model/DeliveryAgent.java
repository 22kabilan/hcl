package com.laundry.model;

import com.laundry.constants.LaundryConstants;

/**
 * Delivery Agent entity responsible for pickup and doorstep delivery.
 */
public class DeliveryAgent extends User {
    private static final long serialVersionUID = 1L;

    private String vehicleNumber;
    private String assignedZone;

    public DeliveryAgent() {
        super();
        this.role = LaundryConstants.ROLE_DELIVERY_AGENT;
    }

    public DeliveryAgent(String id, String name, String phone, String email, String vehicleNumber, String assignedZone) {
        super(id, name, phone, email, LaundryConstants.ROLE_DELIVERY_AGENT);
        this.vehicleNumber = vehicleNumber;
        this.assignedZone = assignedZone;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getAssignedZone() {
        return assignedZone;
    }

    public void setAssignedZone(String assignedZone) {
        this.assignedZone = assignedZone;
    }

    @Override
    public String getRoleDescription() {
        return "Logistics agent handling scheduled pickup collection and final delivery fulfillment.";
    }
}
