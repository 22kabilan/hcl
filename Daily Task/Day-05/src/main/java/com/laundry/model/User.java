package com.laundry.model;

/**
 * User (Day 05 Project Task)
 * Abstract base class for the system role hierarchy.
 */
public abstract class User extends BaseEntity {

    protected String fullName;
    protected String email;
    protected String phone;
    protected final String role;

    public User(String id, String fullName, String email, String phone, String role) {
        super(id);
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    // Abstract method defining role-specific primary responsibility
    public abstract String getRolePermissionSummary();

    @Override
    public String toString() {
        return String.format("%s[ID=%s, Name='%s', Role=%s]", getClass().getSimpleName(), id, fullName, role);
    }
}
