package com.laundry.model;

import java.util.Objects;

/**
 * User
 * 
 * Abstract base class representing any authenticated system actor.
 * Forms the root of the Role Hierarchy (Admin, Staff, DeliveryAgent, Customer).
 */
public abstract class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String phone;
    protected String email;
    protected String role;

    public User() {
        super();
    }

    public User(String id, String name, String phone, String email, String role) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Polymorphic method to describe user permissions and role details
     */
    public abstract String getRoleDescription();

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
