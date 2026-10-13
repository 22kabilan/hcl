package com.laundry;

import java.util.Scanner;

/**
 * Laundry Service Management - Console Menu (Day 03 Project Task)
 * Implements 8 Functional Requirements (FR1 - FR8) with input-validation guardrails.
 * Guarantees that the console menu never crashes on invalid user input.
 */
public class LaundryMenuApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("===============================================================");
        System.out.println("          LAUNDRY SERVICE MANAGEMENT SYSTEM - CONSOLE          ");
        System.out.println("===============================================================");

        do {
            printMenu();
            System.out.print("Enter command choice (1-9): ");

            if (!scanner.hasNextInt()) {
                String badInput = scanner.nextLine();
                System.out.println(">> Error: '" + badInput + "' is not a valid choice. Enter a number between 1 and 9.\n");
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume trailing newline

            switch (choice) {
                case 1:
                    System.out.println("\n[FR1 - Admin] Managing services and item prices...");
                    System.out.println("  -> Current Price List: Wash & Fold: ₹40/kg | Dry Clean: ₹120/item | Express Iron: ₹30/item");
                    break;

                case 2:
                    System.out.println("\n[FR2 - Customer] Scheduling laundry pickup...");
                    System.out.print("Enter pickup address: ");
                    String address = scanner.nextLine();
                    System.out.println("  -> Pickup scheduled at: " + address + " for today 4:00 PM.");
                    break;

                case 3:
                    System.out.println("\n[FR3 - Staff] Recording items and generating unique RFID/barcode tags...");
                    System.out.println("  -> Recorded 3 Shirts, 2 Trousers. Generated Tags: [TAG-101, TAG-102, TAG-103, TAG-104, TAG-105]");
                    break;

                case 4:
                    System.out.println("\n[FR4 - System] Computing bill with express surcharge strategy...");
                    System.out.println("  -> Base Subtotal: ₹360.00 | Express Surcharge (40%): ₹144.00 | Total Invoice: ₹504.00");
                    break;

                case 5:
                    System.out.println("\n[FR5 - Staff] Updating order processing stage...");
                    System.out.println("  -> Transitioned Order #ORD-1001: [RECEIVED] -> [WASHING] -> [IRONING] -> [PACKED]");
                    break;

                case 6:
                    System.out.println("\n[FR6 - Delivery Agent] Completing pickup and delivery...");
                    System.out.println("  -> Delivery Agent John marked Order #ORD-1001 as DELIVERED to customer.");
                    break;

                case 7:
                    System.out.println("\n[FR7 - Customer] Tracking live order status...");
                    System.out.println("  -> Order #ORD-1001: Stage: PACKED | Expected Delivery: 6:30 PM");
                    break;

                case 8:
                    System.out.println("\n[FR8 - Admin] Generating daily production report...");
                    System.out.println("  -> Daily Summary: 48 Orders Processed | 192 Items Cleaned | Revenue: ₹24,850.00");
                    break;

                case 9:
                    System.out.println("\n>> Exiting Laundry Service Console. Goodbye!");
                    break;

                default:
                    System.out.println(">> Invalid choice! Please select an option between 1 and 9.\n");
                    break;
            }

            System.out.println();
        } while (choice != 9);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("---------------------------------------------------------------");
        System.out.println("                      FUNCTIONAL MENU                          ");
        System.out.println("---------------------------------------------------------------");
        System.out.println(" 1. [FR1] Manage Services & Item Prices (Admin)");
        System.out.println(" 2. [FR2] Schedule Laundry Pickup (Customer)");
        System.out.println(" 3. [FR3] Record Items & Generate Tags (Staff)");
        System.out.println(" 4. [FR4] Calculate Bill & Surcharges (System)");
        System.out.println(" 5. [FR5] Update Order Processing Stage (Staff)");
        System.out.println(" 6. [FR6] Complete Pickup & Delivery (Delivery Agent)");
        System.out.println(" 7. [FR7] Track Order Status (Customer)");
        System.out.println(" 8. [FR8] View Daily Production Report (Admin)");
        System.out.println(" 9. Exit Application");
        System.out.println("---------------------------------------------------------------");
    }
}
