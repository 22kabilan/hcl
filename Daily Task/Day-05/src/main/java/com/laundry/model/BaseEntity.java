package com.laundry.model;

import java.time.LocalDateTime;

/**
 * BaseEntity (Day 05 Project Task)
 * Common abstract base class providing primary identity and audit fields.
 */
public abstract class BaseEntity {

    protected final String id;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected boolean active;

    public BaseEntity(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Entity ID cannot be null or empty.");
        }
        this.id = id.trim();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        touch();
    }
}
