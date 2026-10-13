package com.laundry.model;

/**
 * Admin role entity
 */
public class Admin extends User {

    public Admin(String id, String fullName, String email, String phone) {
        super(id, fullName, email, phone, "ADMIN");
    }

    @Override
    public String getRolePermissionSummary() {
        return "Manage services, configure item price catalogues, and view daily production reports.";
    }
}
