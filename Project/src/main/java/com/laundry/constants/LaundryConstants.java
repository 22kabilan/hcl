package com.laundry.constants;

/**
 * LaundryConstants
 * 
 * Centralized repository for all business rules, pricing standards,
 * stage workflows, and one week of sample seed data stored in arrays.
 * Developed as part of Day 02 Language Fundamentals.
 */
public final class LaundryConstants {

    // Prevent instantiation of utility constants class
    private LaundryConstants() {}

    // ==========================================
    // BUSINESS RULES & FINANCIAL POLICIES
    // ==========================================
    public static final String CURRENCY = "INR";
    public static final double EXPRESS_SURCHARGE_RATE = 0.50; // 50% extra for express orders
    public static final double STANDARD_SURCHARGE_RATE = 0.00; // No surcharge for standard orders
    public static final double GST_TAX_RATE = 0.05; // 5% GST on laundry services
    public static final double MINIMUM_ORDER_VALUE = 100.00; // Minimum order threshold
    public static final int STANDARD_DELIVERY_DAYS = 3;
    public static final int EXPRESS_DELIVERY_DAYS = 1;

    // Tag generation settings
    public static final String TAG_PREFIX = "TAG";
    public static final String ORDER_PREFIX = "ORD";
    public static final String PICKUP_PREFIX = "PCK";

    // Valid processing stages in order of progression
    public static final String[] VALID_STAGES = {
        "RECEIVED",
        "WASHING",
        "IRONING",
        "PACKING",
        "READY_FOR_DELIVERY",
        "DELIVERED"
    };

    // User Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_DELIVERY_AGENT = "DELIVERY_AGENT";
    public static final String ROLE_CUSTOMER = "CUSTOMER";

    // ==========================================
    // SAMPLE DATA IN ARRAYS (ONE WEEK OF DATA)
    // ==========================================

    // Days of the operating week
    public static final String[] DAYS_OF_WEEK = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    // Initial catalog of services and item prices: [Service ID, Service Name, Garment Item, Unit Price]
    public static final String[][] DEFAULT_SERVICES_CATALOG = {
        {"SVC-01", "Wash & Fold", "Shirt/T-Shirt", "30.00"},
        {"SVC-02", "Wash & Fold", "Trousers/Jeans", "40.00"},
        {"SVC-03", "Wash & Iron", "Formal Shirt", "50.00"},
        {"SVC-04", "Wash & Iron", "Formal Trousers", "60.00"},
        {"SVC-05", "Dry Clean", "Blazer/Suit", "180.00"},
        {"SVC-06", "Dry Clean", "Silk Saree", "200.00"},
        {"SVC-07", "Heavy Wash", "Bedsheet (Double)", "120.00"},
        {"SVC-08", "Heavy Wash", "Blanket/Comforter", "250.00"}
    };

    // Daily pickup capacity / volume processed across the 7 days
    public static final int[] DAILY_PICKUP_CAPACITY = {
        25, 30, 28, 35, 40, 55, 50
    };

    // 2-D Array: Weekly simulated order counts across 7 days for 4 core service categories
    // Rows: Monday to Sunday (7 days)
    // Columns: [Wash & Fold, Wash & Iron, Dry Clean, Heavy Wash]
    public static final int[][] WEEKLY_ORDERS_MATRIX = {
        {15, 20, 8, 4},   // Monday
        {18, 22, 10, 5},  // Tuesday
        {16, 25, 9, 6},   // Wednesday
        {20, 28, 12, 7},  // Thursday
        {25, 35, 15, 8},  // Friday
        {35, 45, 22, 14}, // Saturday (peak)
        {30, 40, 20, 12}  // Sunday (peak)
    };

    // Sample pre-registered customers: [Customer ID, Full Name, Phone, Address]
    public static final String[][] SAMPLE_CUSTOMERS = {
        {"CUST-001", "Ananya Sharma", "9876543210", "12, Lake View Apts, Indiranagar"},
        {"CUST-002", "Rahul Dravid", "9876543211", "45, Brigade Road, Ashok Nagar"},
        {"CUST-003", "Priya Venkatesh", "9876543212", "88, Green Glen Layout, Bellandur"},
        {"CUST-004", "Karthik Raja", "9876543213", "102, Palm Meadows, Whitefield"},
        {"CUST-005", "Sneha Roy", "9876543214", "15, 4th Cross, Koramangala"}
    };

    // Sample weekly pickup requests: [Pickup ID, Customer Name, Address, Time Slot, Speed (Standard/Express)]
    public static final String[][] SAMPLE_WEEKLY_PICKUPS = {
        {"PCK-101", "Ananya Sharma", "12, Lake View Apts", "Mon 09:00 AM - 11:00 AM", "Express"},
        {"PCK-102", "Rahul Dravid", "45, Brigade Road", "Mon 11:00 AM - 01:00 PM", "Standard"},
        {"PCK-103", "Priya Venkatesh", "88, Green Glen Layout", "Tue 10:00 AM - 12:00 PM", "Standard"},
        {"PCK-104", "Karthik Raja", "102, Palm Meadows", "Wed 04:00 PM - 06:00 PM", "Express"},
        {"PCK-105", "Sneha Roy", "15, 4th Cross", "Thu 02:00 PM - 04:00 PM", "Standard"},
        {"PCK-106", "Ananya Sharma", "12, Lake View Apts", "Fri 09:00 AM - 11:00 AM", "Standard"},
        {"PCK-107", "Karthik Raja", "102, Palm Meadows", "Sat 11:00 AM - 01:00 PM", "Express"}
    };
}
