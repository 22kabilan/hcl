package com.laundry.app;

import com.laundry.exception.DuplicateTagException;
import com.laundry.exception.InvalidStageTransitionException;
import com.laundry.exception.LaundryServiceException;
import com.laundry.exception.OrderNotFoundException;
import com.laundry.model.*;
import com.laundry.service.LaundryService;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * LaundryConsoleApp
 * 
 * Interactive Console Application for the Laundry Service Management System.
 * Meets requirements for:
 * - Day 03: Console menu with one option per Functional Requirement (FR1–FR8).
 * - Day 06: Exception handling & business rule enforcement with automatic menu error recovery.
 * - Day 07: Live execution of FR1 (Admin catalog), FR2 (Pickup scheduling), and FR3 (Garment tagging).
 */
public class LaundryConsoleApp {

    private final LaundryService laundryService;
    private final Scanner scanner;

    public LaundryConsoleApp() {
        this.laundryService = new LaundryService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LaundryConsoleApp app = new LaundryConsoleApp();
        app.run();
    }

    public void run() {
        boolean running = true;
        System.out.println("==================================================================");
        System.out.println("       WELCOME TO LAUNDRY SERVICE MANAGEMENT SYSTEM (HCL)         ");
        System.out.println("==================================================================");

        while (running) {
            displayMenu();
            System.out.print("Enter your choice (0-8): ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        handleFR1ManageServicesAndPrices();
                        break;
                    case 2:
                        handleFR2CustomerSchedulePickup();
                        break;
                    case 3:
                        handleFR3StaffRecordItemsAndGenerateTags();
                        break;
                    case 4:
                        handleFR4ComputeBillAndInvoice();
                        break;
                    case 5:
                        handleFR5StaffUpdateProcessingStage();
                        break;
                    case 6:
                        handleFR6AgentCompletePickupOrDelivery();
                        break;
                    case 7:
                        handleFR7CustomerTrackOrderStatus();
                        break;
                    case 8:
                        handleFR8AdminDailyProductionReport();
                        break;
                    case 0:
                        System.out.println("\nThank you for using Laundry Service Management. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("\n[!] Invalid option. Please select a number from 0 to 8.");
                }
            } catch (NumberFormatException e) {
                // Day 06 Error Recovery: Invalid non-numeric input gracefully handled
                System.out.println("\n[!] Input Error: Please enter a valid numerical choice.");
            } catch (InvalidStageTransitionException e) {
                // Day 06 Business Rule 1 Enforced: Invalid stage transition caught
                System.out.println("\n[X] BUSINESS RULE VIOLATION (Stage Workflow):");
                System.out.println("    " + e.getMessage());
            } catch (DuplicateTagException e) {
                // Day 06 Business Rule 2 Enforced: Duplicate tag rejection caught
                System.out.println("\n[X] BUSINESS RULE VIOLATION (Tag Uniqueness):");
                System.out.println("    " + e.getMessage());
            } catch (OrderNotFoundException e) {
                System.out.println("\n[X] LOOKUP ERROR: " + e.getMessage());
            } catch (LaundryServiceException e) {
                System.out.println("\n[X] SERVICE ERROR: " + e.getMessage());
            } catch (Exception e) {
                // General exception safety - console app always recovers
                System.out.println("\n[X] UNEXPECTED SYSTEM ERROR: " + e.getMessage());
            }

            if (running) {
                System.out.println("\n------------------------------------------------------------------");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=========================== MAIN MENU ============================");
        System.out.println("1. [FR1 - Admin] Manage Services & Item Prices");
        System.out.println("2. [FR2 - Customer] Schedule Pickup");
        System.out.println("3. [FR3 - Staff] Record Items & Generate Unique Tags");
        System.out.println("4. [FR4 - System] Compute Bill & View Invoice (incl. Express Surcharge)");
        System.out.println("5. [FR5 - Staff] Update Processing Stage (Wash -> Iron -> Pack)");
        System.out.println("6. [FR6 - Delivery Agent] Complete Pickup / Dispatch Delivery");
        System.out.println("7. [FR7 - Customer] Track Order Status");
        System.out.println("8. [FR8 - Admin] View Daily Production Report");
        System.out.println("0. Exit Application");
        System.out.println("==================================================================");
    }

    // =========================================================================
    // FR1: ADMIN MANAGES SERVICES AND ITEM PRICES
    // =========================================================================
    private void handleFR1ManageServicesAndPrices() throws LaundryServiceException {
        System.out.println("\n--- [FR1] Manage Services & Item Prices ---");
        System.out.println("a. View All Service Catalog Items");
        System.out.println("b. Add New Service Item & Price");
        System.out.println("c. Update Price of Existing Service Item");
        System.out.print("Select action (a/b/c): ");
        String sub = scanner.nextLine().trim().toLowerCase();

        if ("a".equals(sub)) {
            List<ServiceItem> items = laundryService.getAllServices();
            System.out.println("\nCURRENT SERVICE CATALOG:");
            for (ServiceItem item : items) {
                System.out.println("  " + item);
            }
        } else if ("b".equals(sub)) {
            System.out.print("Enter Service Category (e.g., Dry Clean, Steam Iron): ");
            String serviceName = scanner.nextLine().trim();
            System.out.print("Enter Garment Item Type (e.g., Woolen Jacket, Curtains): ");
            String itemType = scanner.nextLine().trim();
            System.out.print("Enter Unit Price (INR): ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            ServiceItem added = laundryService.addServiceItem(serviceName, itemType, price);
            System.out.println("[✓] Successfully added new service item: " + added);
        } else if ("c".equals(sub)) {
            System.out.print("Enter Service ID to update (e.g., SVC-01): ");
            String svcId = scanner.nextLine().trim();
            System.out.print("Enter New Unit Price (INR): ");
            double newPrice = Double.parseDouble(scanner.nextLine().trim());

            boolean updated = laundryService.updateServicePrice(svcId, newPrice);
            if (updated) {
                System.out.println("[✓] Price updated successfully. Note: Existing orders preserve historical rates (NFR-P4).");
            } else {
                System.out.println("[!] Service ID not found: " + svcId);
            }
        } else {
            System.out.println("[!] Invalid sub-menu choice.");
        }
    }

    // =========================================================================
    // FR2: CUSTOMER SCHEDULES PICKUP
    // =========================================================================
    private void handleFR2CustomerSchedulePickup() throws LaundryServiceException {
        System.out.println("\n--- [FR2] Schedule Laundry Pickup ---");
        System.out.print("Enter Customer Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter Pickup Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter Preferred Time Slot (e.g., Today 10:00 AM - 12:00 PM): ");
        String slot = scanner.nextLine().trim();
        System.out.print("Do you require Express Delivery (+50% surcharge, 24h SLA)? (y/n): ");
        boolean isExpress = scanner.nextLine().trim().equalsIgnoreCase("y");

        PickupRequest request = laundryService.schedulePickup(name, phone, address, slot, isExpress);
        System.out.println("[✓] Pickup scheduled successfully!");
        System.out.println("    Request ID: " + request.getId());
        System.out.println("    Service Mode: " + request.getStrategy().getStrategyName());
        System.out.println("    Turnaround SLA: " + request.getStrategy().getDeliveryTurnaroundDays() + " Day(s)");
        System.out.println("    Logistics Status: Enqueued in FIFO Logistics Queue");
    }

    // =========================================================================
    // FR3: STAFF RECORDS ITEMS AND GENERATES TAGS
    // =========================================================================
    private void handleFR3StaffRecordItemsAndGenerateTags() throws LaundryServiceException {
        System.out.println("\n--- [FR3] Staff: Record Items & Generate Unique Tags ---");
        List<LaundryOrder> orders = laundryService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("[!] No active orders found. Please dispatch a pickup first.");
            return;
        }

        System.out.println("ACTIVE ORDERS AVAILABLE FOR RECORDING:");
        for (LaundryOrder o : orders) {
            System.out.println("  " + o);
        }

        System.out.print("\nEnter Order ID to add items to (e.g., ORD-101): ");
        String orderId = scanner.nextLine().trim();

        System.out.println("\nAVAILABLE SERVICES IN CATALOG:");
        List<ServiceItem> catalog = laundryService.getAllServices();
        for (int i = 0; i < catalog.size(); i++) {
            System.out.printf("  %d. %s\n", (i + 1), catalog.get(i));
        }

        System.out.print("\nSelect Catalog Item Number (1-" + catalog.size() + ") or type 'custom': ");
        String selection = scanner.nextLine().trim();

        String itemType;
        String serviceName;
        double unitPrice;

        if ("custom".equalsIgnoreCase(selection)) {
            System.out.print("Enter Item Type (e.g., Silk Shirt): ");
            itemType = scanner.nextLine().trim();
            System.out.print("Enter Service (e.g., Dry Clean): ");
            serviceName = scanner.nextLine().trim();
            System.out.print("Enter Unit Price (INR): ");
            unitPrice = Double.parseDouble(scanner.nextLine().trim());
        } else {
            int idx = Integer.parseInt(selection) - 1;
            if (idx < 0 || idx >= catalog.size()) {
                System.out.println("[!] Invalid item number.");
                return;
            }
            ServiceItem chosen = catalog.get(idx);
            itemType = chosen.getItemType();
            serviceName = chosen.getServiceName();
            unitPrice = chosen.getUnitPrice();
        }

        System.out.print("Enter Quantity of garments (pieces): ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        OrderItem recorded = laundryService.recordItemForOrder(orderId, itemType, serviceName, qty, unitPrice);
        System.out.println("\n[✓] Item recorded successfully!");
        System.out.println("    Line Item: " + recorded.getItemType() + " x" + recorded.getQuantity());
        System.out.println("    Subtotal: INR " + recorded.getSubtotal());
        System.out.println("    Generated Unique Tags (NFR-P1 Verified): " + recorded.getGarmentTags());
    }

    // =========================================================================
    // FR4: SYSTEM COMPUTES BILL INCL. EXPRESS SURCHARGES
    // =========================================================================
    private void handleFR4ComputeBillAndInvoice() throws LaundryServiceException {
        System.out.println("\n--- [FR4] Compute Bill & View Invoice ---");
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine().trim();

        String invoice = laundryService.generateInvoiceSummary(orderId);
        System.out.println(invoice);
    }

    // =========================================================================
    // FR5: STAFF UPDATES PROCESSING STAGE
    // =========================================================================
    private void handleFR5StaffUpdateProcessingStage() throws LaundryServiceException {
        System.out.println("\n--- [FR5] Staff: Update Processing Stage ---");
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine().trim();

        LaundryOrder order = laundryService.findOrderById(orderId);
        System.out.println("Current Stage for Order " + orderId + ": [" + order.getCurrentStage() + "]");
        System.out.println("\nALLOWED STAGE WORKFLOW:");
        System.out.println("  1. RECEIVED");
        System.out.println("  2. WASHING");
        System.out.println("  3. IRONING");
        System.out.println("  4. PACKING");
        System.out.println("  5. READY_FOR_DELIVERY");
        System.out.println("  6. DELIVERED");

        System.out.print("\nEnter Staff Member Name: ");
        String staffName = scanner.nextLine().trim();

        System.out.print("Enter Next Stage Number (1-6): ");
        int stageNum = Integer.parseInt(scanner.nextLine().trim());

        if (stageNum < 1 || stageNum > 6) {
            System.out.println("[!] Invalid stage selection.");
            return;
        }

        OrderStage nextStage = OrderStage.values()[stageNum - 1];
        OrderStage updated = laundryService.advanceOrderStage(orderId, nextStage, staffName);
        System.out.println("[✓] Stage successfully advanced to: [" + updated + "]");
    }

    // =========================================================================
    // FR6: AGENT COMPLETES PICKUP / DELIVERY
    // =========================================================================
    private void handleFR6AgentCompletePickupOrDelivery() throws LaundryServiceException {
        System.out.println("\n--- [FR6] Delivery Agent Logistics ---");
        System.out.println("1. Collect Next Scheduled Pickup from Logistics Queue");
        System.out.println("2. Mark Order as Delivered to Customer Doorstep");
        System.out.print("Choose action (1/2): ");
        String action = scanner.nextLine().trim();

        if ("1".equals(action)) {
            Queue<PickupRequest> pending = laundryService.getPendingPickups();
            System.out.println("Pending Pickups in Logistics Queue: " + pending.size());
            if (pending.isEmpty()) {
                System.out.println("[!] No pending pickups in queue.");
                return;
            }
            System.out.print("Enter Delivery Agent Name: ");
            String agent = scanner.nextLine().trim();

            LaundryOrder newOrder = laundryService.processPickupFromQueue(agent);
            System.out.println("[✓] Pickup completed and transferred to workshop inventory!");
            System.out.println("    Created Order ID: " + newOrder.getId());
            System.out.println("    Customer: " + newOrder.getCustomer().getName());
            System.out.println("    Service Mode: " + newOrder.getSurchargeStrategy().getStrategyName());
        } else if ("2".equals(action)) {
            System.out.print("Enter Order ID to mark delivered: ");
            String orderId = scanner.nextLine().trim();
            laundryService.markOrderDelivered(orderId);
            System.out.println("[✓] Order " + orderId + " marked as DELIVERED and payment confirmed.");
        } else {
            System.out.println("[!] Invalid action.");
        }
    }

    // =========================================================================
    // FR7: CUSTOMER TRACKS ORDER STATUS
    // =========================================================================
    private void handleFR7CustomerTrackOrderStatus() throws LaundryServiceException {
        System.out.println("\n--- [FR7] Customer Order Tracking ---");
        System.out.print("Enter Order ID to track (e.g., ORD-101): ");
        String orderId = scanner.nextLine().trim();

        LaundryOrder order = laundryService.getOrderTrackingInfo(orderId);
        System.out.println("\n=======================================================");
        System.out.println("                   ORDER TRACKING STATUS               ");
        System.out.println("=======================================================");
        System.out.println("Order ID:           " + order.getId());
        System.out.println("Customer:           " + order.getCustomer().getName());
        System.out.println("Current Stage:      " + order.getCurrentStage().name() + " (" + order.getCurrentStage().getDescription() + ")");
        System.out.println("Delivery Service:   " + order.getSurchargeStrategy().getStrategyName());
        System.out.println("Estimated SLA Due:  " + order.getDeliveryDueDate().toLocalDate());
        System.out.println("Total Items:        " + order.getTotalGarmentsCount() + " pieces");
        System.out.println("Total Bill:         INR " + String.format("%.2f", order.calculateTotalBill()));
        System.out.println("\nAUDIT LOG TIMELINE:");
        for (String log : order.getStageHistoryLogs()) {
            System.out.println("  • " + log);
        }
        System.out.println("=======================================================");
    }

    // =========================================================================
    // FR8: ADMIN VIEWS DAILY PRODUCTION REPORT
    // =========================================================================
    private void handleFR8AdminDailyProductionReport() {
        System.out.println("\n--- [FR8] Admin: Daily Production Report ---");
        String report = laundryService.generateDailyProductionReport();
        System.out.println(report);
    }
}
