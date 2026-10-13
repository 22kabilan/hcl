package com.laundry.service;

import com.laundry.constants.LaundryConstants;
import com.laundry.exception.DuplicateTagException;
import com.laundry.exception.InvalidStageTransitionException;
import com.laundry.exception.LaundryServiceException;
import com.laundry.exception.OrderNotFoundException;
import com.laundry.model.*;
import com.laundry.strategy.ExpressSurchargeStrategy;
import com.laundry.strategy.StandardSurchargeStrategy;
import com.laundry.strategy.SurchargeStrategy;

import java.time.LocalDate;
import java.util.*;

/**
 * LaundryService
 * 
 * Core business service managing laundry operations.
 * Implements Day 07 Collections Framework requirements:
 * 
 * COLLECTION CHOICES & JUSTIFICATIONS:
 * 1. List<ServiceItem> (ArrayList):
 *    - Justification: Fast random-access read O(1) for displaying catalogs and indexing.
 * 2. List<LaundryOrder> (ArrayList):
 *    - Justification: Maintains insertion-order history of orders; easy iteration for reporting.
 * 3. Set<String> (HashSet):
 *    - Justification: Enforces NFR-P1 (Tag Uniqueness); provides O(1) duplicate checks and lookup.
 * 4. Queue<PickupRequest> (ArrayDeque):
 *    - Justification: FIFO queuing for pickup scheduling. ArrayDeque is faster and more cache-friendly
 *      than LinkedList without node allocation overhead.
 * 5. Map<String, Customer> (LinkedHashMap):
 *    - Justification: O(1) lookup by customer phone/ID with predictable insertion order.
 */
public class LaundryService {

    // 1. LIST: Catalog of available services and item prices (FR1)
    private final List<ServiceItem> serviceCatalog;

    // 2. LIST: Registry of all historical and active laundry orders
    private final List<LaundryOrder> ordersList;

    // 3. SET: Global registry of assigned item tags ensuring absolute uniqueness (FR3, NFR-P1)
    private final Set<String> globalTagRegistry;

    // 4. QUEUE: FIFO pickup scheduling queue for logistics agent dispatch (FR2, FR6)
    private final Queue<PickupRequest> pickupQueue;

    // 5. MAP: Customer registry for fast lookups
    private final Map<String, Customer> customerRegistry;

    private int orderCounter = 101;
    private int pickupCounter = 207;
    private int serviceCounter = 8;
    private int tagCounter = 1000;

    public LaundryService() {
        this.serviceCatalog = new ArrayList<>();
        this.ordersList = new ArrayList<>();
        this.globalTagRegistry = new HashSet<>();
        this.pickupQueue = new ArrayDeque<>();
        this.customerRegistry = new LinkedHashMap<>();

        seedInitialData();
    }

    /**
     * Seeds catalog and sample customers from Day 02 Constants arrays.
     */
    private void seedInitialData() {
        // Seed Catalog from 2D Array
        for (String[] entry : LaundryConstants.DEFAULT_SERVICES_CATALOG) {
            String id = entry[0];
            String name = entry[1];
            String itemType = entry[2];
            double price = Double.parseDouble(entry[3]);
            serviceCatalog.add(new ServiceItem(id, name, itemType, price));
        }

        // Seed Customers from 2D Array
        for (String[] custData : LaundryConstants.SAMPLE_CUSTOMERS) {
            Customer cust = new Customer(custData[0], custData[1], custData[2], custData[3]);
            customerRegistry.put(cust.getPhone(), cust);
        }

        // Seed Initial Scheduled Pickups
        for (String[] pckData : LaundryConstants.SAMPLE_WEEKLY_PICKUPS) {
            Customer customer = findOrCreateCustomer(pckData[1], "98000" + (pickupCounter), pckData[2]);
            SurchargeStrategy strategy = "Express".equalsIgnoreCase(pckData[4])
                    ? new ExpressSurchargeStrategy()
                    : new StandardSurchargeStrategy();
            PickupRequest req = new PickupRequest(pckData[0], customer, pckData[2], pckData[3], strategy);
            pickupQueue.offer(req);
        }

        // Seed a sample active order to facilitate immediate testing
        try {
            Customer c1 = customerRegistry.values().iterator().next();
            LaundryOrder demoOrder = new LaundryOrder("ORD-101", c1, new ExpressSurchargeStrategy(), c1.getAddress(), "Morning");
            OrderItem item1 = new OrderItem("ITEM-01", "Formal Shirt", "Wash & Iron", 2, 50.00);
            generateAndAttachTags("ORD-101", item1);
            demoOrder.addItem(item1);

            OrderItem item2 = new OrderItem("ITEM-02", "Blazer/Suit", "Dry Clean", 1, 180.00);
            generateAndAttachTags("ORD-101", item2);
            demoOrder.addItem(item2);

            ordersList.add(demoOrder);
        } catch (LaundryServiceException ignored) {}
    }

    // =========================================================================
    // FR1: ADMIN MANAGES SERVICES AND ITEM PRICES
    // =========================================================================

    public List<ServiceItem> getAllServices() {
        return Collections.unmodifiableList(serviceCatalog);
    }

    public ServiceItem addServiceItem(String serviceName, String itemType, double unitPrice) throws LaundryServiceException {
        if (serviceName == null || serviceName.trim().isEmpty()) {
            throw new LaundryServiceException("Service name cannot be empty.");
        }
        if (itemType == null || itemType.trim().isEmpty()) {
            throw new LaundryServiceException("Item type cannot be empty.");
        }
        if (unitPrice <= 0) {
            throw new LaundryServiceException("Unit price must be strictly positive.");
        }

        String id = "SVC-" + (++serviceCounter);
        ServiceItem newItem = new ServiceItem(id, serviceName.trim(), itemType.trim(), unitPrice);
        serviceCatalog.add(newItem);
        return newItem;
    }

    public boolean updateServicePrice(String serviceId, double newPrice) throws LaundryServiceException {
        if (newPrice <= 0) {
            throw new LaundryServiceException("Updated price must be strictly positive.");
        }
        for (ServiceItem item : serviceCatalog) {
            if (item.getId().equalsIgnoreCase(serviceId)) {
                // Non-retroactivity guaranteed: modifying catalog price does not mutate existing order items
                item.setUnitPrice(newPrice);
                return true;
            }
        }
        return false;
    }

    // =========================================================================
    // FR2: CUSTOMER SCHEDULES PICKUP
    // =========================================================================

    public PickupRequest schedulePickup(String customerName, String phone, String address, String timeSlot, boolean isExpress) throws LaundryServiceException {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new LaundryServiceException("Customer name is required.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new LaundryServiceException("Phone number is required.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new LaundryServiceException("Pickup address is required.");
        }

        Customer customer = findOrCreateCustomer(customerName.trim(), phone.trim(), address.trim());
        SurchargeStrategy strategy = isExpress ? new ExpressSurchargeStrategy() : new StandardSurchargeStrategy();

        String pickupId = LaundryConstants.PICKUP_PREFIX + "-" + (++pickupCounter);
        PickupRequest request = new PickupRequest(pickupId, customer, address.trim(), timeSlot, strategy);

        // Enqueue into FIFO Queue
        pickupQueue.offer(request);
        return request;
    }

    public Queue<PickupRequest> getPendingPickups() {
        return new ArrayDeque<>(pickupQueue);
    }

    // =========================================================================
    // FR3: STAFF RECORDS ITEMS AND GENERATES UNIQUE TAGS
    // =========================================================================

    public LaundryOrder createOrderForCustomer(Customer customer, SurchargeStrategy strategy, String address, String slot) {
        String orderId = LaundryConstants.ORDER_PREFIX + "-" + (++orderCounter);
        LaundryOrder order = new LaundryOrder(orderId, customer, strategy, address, slot);
        customer.incrementOrdersCount();
        ordersList.add(order);
        return order;
    }

    public OrderItem recordItemForOrder(String orderId, String itemType, String serviceName, int quantity, double unitPrice)
            throws LaundryServiceException {
        LaundryOrder order = findOrderById(orderId);
        if (quantity <= 0) {
            throw new LaundryServiceException("Quantity must be at least 1.");
        }
        if (unitPrice <= 0) {
            throw new LaundryServiceException("Unit price must be positive.");
        }

        String itemId = "ITEM-" + order.getItems().size() + 1;
        OrderItem orderItem = new OrderItem(itemId, itemType, serviceName, quantity, unitPrice);

        // Generate and attach unique tags for each individual garment piece
        generateAndAttachTags(orderId, orderItem);

        order.addItem(orderItem);
        return orderItem;
    }

    /**
     * Generates a globally unique tag for each garment piece.
     * Enforces NFR-P1 (Tag Uniqueness) and Business Rule 2.
     */
    public void generateAndAttachTags(String orderId, OrderItem item) throws DuplicateTagException {
        for (int i = 1; i <= item.getQuantity(); i++) {
            String sanitizedType = item.getItemType().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            if (sanitizedType.length() > 5) sanitizedType = sanitizedType.substring(0, 5);
            
            String candidateTag = String.format("%s-%s-%s-%04d",
                    LaundryConstants.TAG_PREFIX,
                    orderId,
                    sanitizedType,
                    (++tagCounter));

            // SET LOOKUP: Check if tag already exists in the global registry
            if (globalTagRegistry.contains(candidateTag)) {
                throw new DuplicateTagException(candidateTag);
            }

            globalTagRegistry.add(candidateTag);
            item.addGarmentTag(candidateTag);
        }
    }

    // =========================================================================
    // FR4: SYSTEM COMPUTES BILL INCL. EXPRESS SURCHARGE
    // =========================================================================

    public String generateInvoiceSummary(String orderId) throws LaundryServiceException {
        LaundryOrder order = findOrderById(orderId);
        StringBuilder sb = new StringBuilder();
        sb.append("\n=======================================================\n");
        sb.append("                  LAUNDRY SERVICE INVOICE              \n");
        sb.append("=======================================================\n");
        sb.append(String.format("Order ID: %-18s Date: %s\n", order.getId(), LocalDate.now()));
        sb.append(String.format("Customer: %-18s Phone: %s\n", order.getCustomer().getName(), order.getCustomer().getPhone()));
        sb.append(String.format("Delivery SLA: %-14s Due Date: %s\n",
                order.getSurchargeStrategy().getStrategyName(),
                order.getDeliveryDueDate().toLocalDate()));
        sb.append("-------------------------------------------------------\n");
        sb.append(String.format("%-18s %-12s %-5s %-10s %-10s\n", "Item", "Service", "Qty", "Rate", "Total"));
        sb.append("-------------------------------------------------------\n");

        for (OrderItem item : order.getItems()) {
            sb.append(String.format("%-18s %-12s %-5d %-10.2f %-10.2f\n",
                    item.getItemType(),
                    item.getServiceName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()));
            sb.append("   Tags: ").append(item.getGarmentTags()).append("\n");
        }

        sb.append("-------------------------------------------------------\n");
        double subtotal = order.calculateSubtotal();
        double surcharge = order.calculateSurcharge();
        double tax = order.calculateTax();
        double total = order.calculateTotalBill();

        sb.append(String.format("Subtotal:                                     INR %10.2f\n", subtotal));
        sb.append(String.format("Surcharge (%s):                      INR %10.2f\n",
                order.getSurchargeStrategy().getStrategyName(), surcharge));
        sb.append(String.format("GST (5%%):                                     INR %10.2f\n", tax));
        sb.append("=======================================================\n");
        sb.append(String.format("GRAND TOTAL:                                  INR %10.2f\n", total));
        sb.append(String.format("Payment Status: %s\n", order.isPaid() ? "PAID" : "PENDING AT DELIVERY"));
        sb.append("=======================================================\n");
        return sb.toString();
    }

    // =========================================================================
    // FR5: STAFF UPDATES PROCESSING STAGE (VALIDATED WORKFLOW)
    // =========================================================================

    /**
     * Advances order processing stage.
     * Enforces Business Rule 1 (NFR-P2) via InvalidStageTransitionException.
     */
    public OrderStage advanceOrderStage(String orderId, OrderStage nextStage, String staffName)
            throws LaundryServiceException {
        LaundryOrder order = findOrderById(orderId);
        OrderStage current = order.getCurrentStage();

        if (!current.canTransitionTo(nextStage)) {
            throw new InvalidStageTransitionException(orderId, current, nextStage);
        }

        order.setCurrentStage(nextStage);
        order.recordStageLog(nextStage, "Updated by staff: " + staffName);
        return nextStage;
    }

    // =========================================================================
    // FR6: AGENT COMPLETES PICKUP / DELIVERY
    // =========================================================================

    public LaundryOrder processPickupFromQueue(String agentName) throws LaundryServiceException {
        if (pickupQueue.isEmpty()) {
            throw new LaundryServiceException("No pending pickups in the logistics queue.");
        }
        PickupRequest request = pickupQueue.poll();
        request.setPickedUp(true);

        LaundryOrder order = createOrderForCustomer(
                request.getCustomer(),
                request.getStrategy(),
                request.getAddress(),
                request.getTimeSlot()
        );
        order.recordStageLog(OrderStage.RECEIVED, "Picked up by Agent: " + agentName);
        return order;
    }

    public void markOrderDelivered(String orderId) throws LaundryServiceException {
        LaundryOrder order = findOrderById(orderId);
        if (order.getCurrentStage() != OrderStage.READY_FOR_DELIVERY) {
            throw new InvalidStageTransitionException(orderId, order.getCurrentStage(), OrderStage.DELIVERED);
        }
        order.setCurrentStage(OrderStage.DELIVERED);
        order.setPaid(true);
        order.recordStageLog(OrderStage.DELIVERED, "Delivered to customer doorstep.");
    }

    // =========================================================================
    // FR7: CUSTOMER TRACKS ORDER STATUS
    // =========================================================================

    public LaundryOrder getOrderTrackingInfo(String orderId) throws LaundryServiceException {
        return findOrderById(orderId);
    }

    // =========================================================================
    // FR8: ADMIN VIEWS DAILY PRODUCTION REPORT
    // =========================================================================

    public String generateDailyProductionReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=======================================================\n");
        sb.append("           DAILY LAUNDRY PRODUCTION REPORT             \n");
        sb.append("=======================================================\n");
        sb.append(String.format("Report Timestamp: %s\n", java.time.LocalDateTime.now()));
        sb.append(String.format("Total Orders in System: %d\n", ordersList.size()));
        sb.append(String.format("Pending Pickups in Queue: %d\n", pickupQueue.size()));
        sb.append(String.format("Total Unique Garment Tags Issued: %d\n", globalTagRegistry.size()));

        // Stage breakdown
        Map<OrderStage, Integer> stageCounts = new EnumMap<>(OrderStage.class);
        for (OrderStage stage : OrderStage.values()) {
            stageCounts.put(stage, 0);
        }

        double totalRevenue = 0.0;
        int standardOrders = 0;
        int expressOrders = 0;
        int totalGarments = 0;

        for (LaundryOrder order : ordersList) {
            stageCounts.put(order.getCurrentStage(), stageCounts.get(order.getCurrentStage()) + 1);
            totalRevenue += order.calculateTotalBill();
            totalGarments += order.getTotalGarmentsCount();
            if (order.getSurchargeStrategy() instanceof ExpressSurchargeStrategy) {
                expressOrders++;
            } else {
                standardOrders++;
            }
        }

        sb.append("-------------------------------------------------------\n");
        sb.append("STAGE PROGRESSION BREAKDOWN:\n");
        for (OrderStage stage : OrderStage.values()) {
            sb.append(String.format("  %-20s : %d orders\n", stage.name(), stageCounts.get(stage)));
        }

        sb.append("-------------------------------------------------------\n");
        sb.append(String.format("Total Garments Processed: %d pieces\n", totalGarments));
        sb.append(String.format("Standard Orders: %d | Express Orders: %d\n", standardOrders, expressOrders));
        sb.append(String.format("Total Billed Production Value: INR %.2f\n", totalRevenue));
        sb.append("=======================================================\n");
        return sb.toString();
    }

    // =========================================================================
    // HELPER METHODS
    // =========================================================================

    public LaundryOrder findOrderById(String orderId) throws OrderNotFoundException {
        if (orderId == null) throw new OrderNotFoundException("null");
        for (LaundryOrder order : ordersList) {
            if (order.getId().equalsIgnoreCase(orderId.trim())) {
                return order;
            }
        }
        throw new OrderNotFoundException(orderId);
    }

    public List<LaundryOrder> getAllOrders() {
        return Collections.unmodifiableList(ordersList);
    }

    public Customer findOrCreateCustomer(String name, String phone, String address) {
        if (customerRegistry.containsKey(phone)) {
            return customerRegistry.get(phone);
        }
        String id = "CUST-" + (customerRegistry.size() + 1);
        Customer newCustomer = new Customer(id, name, phone, address);
        customerRegistry.put(phone, newCustomer);
        return newCustomer;
    }
}
