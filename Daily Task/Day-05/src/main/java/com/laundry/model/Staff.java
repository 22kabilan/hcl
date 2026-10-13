package com.laundry.model;

/**
 * Staff role entity
 */
public class Staff extends User {

    private String stationName; // e.g., "Washing", "Ironing", "Packaging"

    public Staff(String id, String fullName, String email, String phone, String stationName) {
        super(id, fullName, email, phone, "STAFF");
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String getRolePermissionSummary() {
        return "Record garment items, generate tags, and update processing stages.";
    }
}
