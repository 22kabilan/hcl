package com.laundry.model;

import com.laundry.constants.LaundryConstants;

/**
 * Admin user entity with administrative rights.
 */
public class Admin extends User {
    private static final long serialVersionUID = 1L;

    private String department;

    public Admin() {
        super();
        this.role = LaundryConstants.ROLE_ADMIN;
    }

    public Admin(String id, String name, String phone, String email, String department) {
        super(id, name, phone, email, LaundryConstants.ROLE_ADMIN);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator with full access to service catalogs, pricing, and daily production reports.";
    }
}
