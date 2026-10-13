package com.laundry.service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * LaundryCollectionService (Day 07 Project Task)
 * Demonstrates:
 * - Storing domain entities in List, Set, and Queue
 * - Implementing FR1, FR2, and FR3
 * - Explicit justification of each collection selection in comments
 */
public class LaundryCollectionService {

    /**
     * ServiceItem Model for Price Catalogue
     */
    public static class ServiceItem {
        private final String code;
        private final String serviceName;
        private double price;

        public ServiceItem(String code, String serviceName, double price) {
            this.code = code;
            this.serviceName = serviceName;
            this.price = price;
        }

        public String getCode() { return code; }
        public String getServiceName() { return serviceName; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return String.format("[%s] %-20s: ₹%.2f", code, serviceName, price);
        }
    }

    /**
     * PickupRequest Model for Customer Logistics
     */
    public static class PickupRequest {
        private final String requestId;
        private final String customerName;
        private final String address;
        private final LocalDateTime requestTime;

        public PickupRequest(String requestId, String customerName, String address) {
            this.requestId = requestId;
            this.customerName = customerName;
            this.address = address;
            this.requestTime = LocalDateTime.now();
        }

        public String getRequestId() { return requestId; }
        public String getCustomerName() { return customerName; }
        public String getAddress() { return address; }

        @Override
        public String toString() {
            return String.format("PickupRequest[ID=%s, Customer='%s', Address='%s']",
                    requestId, customerName, address);
        }
    }

    /*
     * COLLECTION CHOICE JUSTIFICATION: List (ArrayList) for FR1
     * Why List:
     * 1. The service and price catalogue requires maintained insertion order and positional indexing.
     * 2. Price lookups by catalog index/position benefit from ArrayList's O(1) indexed random access.
     */
    private final List<ServiceItem> serviceCatalogue = new ArrayList<>();

    /*
     * COLLECTION CHOICE JUSTIFICATION: Queue (ArrayDeque) for FR2
     * Why Queue:
     * 1. Customer pickup requests follow strict First-In-First-Out (FIFO) scheduling order.
     * 2. ArrayDeque provides faster enqueuing and dequeuing with lower memory overhead than LinkedList.
     */
    private final Queue<PickupRequest> pickupQueue = new ArrayDeque<>();

    /*
     * COLLECTION CHOICE JUSTIFICATION: Set (LinkedHashSet) for FR3
     * Why Set:
     * 1. Enforces business rule NFR-P1: RFID and barcode garment tags must be strictly UNIQUE per item.
     * 2. LinkedHashSet prevents duplicate tag assignment with O(1) membership checking while retaining tag creation chronology.
     */
    private final Set<String> activeItemTags = new LinkedHashSet<>();

    public LaundryCollectionService() {
        // FR1 Initial Setup
        serviceCatalogue.add(new ServiceItem("SRV-01", "Wash & Fold", 40.0));
        serviceCatalogue.add(new ServiceItem("SRV-02", "Dry Cleaning", 120.0));
        serviceCatalogue.add(new ServiceItem("SRV-03", "Steam Ironing", 30.0));
    }

    // FR1: Admin manages services and item prices
    public void updatePrice(String serviceCode, double newPrice) {
        for (ServiceItem item : serviceCatalogue) {
            if (item.getCode().equalsIgnoreCase(serviceCode)) {
                item.setPrice(newPrice);
                System.out.printf(">> [FR1] Updated price for %s to ₹%.2f%n", item.getServiceName(), newPrice);
                return;
            }
        }
        System.out.println(">> [FR1 Error] Service not found: " + serviceCode);
    }

    public List<ServiceItem> getServiceCatalogue() {
        return Collections.unmodifiableList(serviceCatalogue);
    }

    // FR2: Customer schedules pickup
    public void schedulePickup(String customerName, String address) {
        String reqId = "REQ-" + (pickupQueue.size() + 101);
        PickupRequest req = new PickupRequest(reqId, customerName, address);
        pickupQueue.offer(req);
        System.out.printf(">> [FR2] Pickup scheduled: %s for '%s'%n", reqId, customerName);
    }

    public PickupRequest dispatchNextPickup() {
        PickupRequest next = pickupQueue.poll();
        if (next != null) {
            System.out.printf(">> [FR2 Logistics] Dispatched driver for: %s%n", next);
        }
        return next;
    }

    // FR3: Staff records items and generates tags (with uniqueness guarantee)
    public boolean recordItemTag(String tagId) {
        if (activeItemTags.contains(tagId)) {
            System.out.printf(">> [FR3 Error] Tag '%s' is already active! Duplicate rejected.%n", tagId);
            return false;
        }
        activeItemTags.add(tagId);
        System.out.printf(">> [FR3] Successfully tagged item with: %s (Total active: %d)%n",
                tagId, activeItemTags.size());
        return true;
    }

    public Set<String> getActiveItemTags() {
        return Collections.unmodifiableSet(activeItemTags);
    }
}
