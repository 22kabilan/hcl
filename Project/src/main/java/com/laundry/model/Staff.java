package com.laundry.model;

import com.laundry.constants.LaundryConstants;

/**
 * Workshop Staff entity responsible for item tagging and processing stage progression.
 */
public class Staff extends User {
    private static final long serialVersionUID = 1L;

    private String station; // e.g., "WASHING_BAY", "PRESSING_DESK", "PACKING_COUNTER"

    public Staff() {
        super();
        this.role = LaundryConstants.ROLE_STAFF;
    }

    public Staff(String id, String name, String phone, String email, String station) {
        super(id, name, phone, email, LaundryConstants.ROLE_STAFF);
        this.station = station;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String getRoleDescription() {
        return "Workshop staff handling garment intake, tagging, washing, ironing, and packing.";
    }
}
