# Laundry Service Management System

A robust, enterprise-grade Core Java application for end-to-end laundry service operations: order intake with item-wise pricing, pickup & delivery logistics scheduling, strict multi-stage garment processing tracking, unique per-item tagging, strategy-driven billing with express surcharges, and administrative production reporting.

---

## 📌 Project Architecture & Domain Modules

The system is architected around a clean modular service design:

| Module | Responsibilities | Target Service |
| :--- | :--- | :--- |
| **M1. Auth & Customer** | User profiles, role hierarchy (Admin, Staff, Delivery Agent, Customer) | `auth-service` |
| **M2. Price List & Catalog**| Service catalog, item-wise pricing, non-retroactive price versioning | `laundry-service` |
| **M3. Orders & Tagging** | Order intake, item breakdown, globally unique garment tagging | `laundry-service` |
| **M4. Pickup & Delivery** | Customer pickup scheduling, delivery dispatch, logistics queues | `logistics-service` |
| **M5. Billing & Reports** | Strategy-based invoicing (Standard vs Express), daily production metrics | `laundry-service` |

---

## 👥 User Roles & Permissions

- **Admin:** Manages services, configures item price lists, monitors business metrics, and inspects daily production reports.
- **Staff:** Inspects collected clothes, records item-level details, generates unique tags per piece, updates processing stages.
- **Delivery Agent:** Manages pickup and delivery routes, completes garment pickup and handover deliveries.
- **Customer:** Schedules pickup requests, selects express or standard delivery, tracks real-time garment stage, views invoices.

---

## 🎯 Functional Requirements (FR1 – FR8)

- **FR1:** Admin manages services and item prices.
- **FR2:** Customer schedules laundry pickup.
- **FR3:** Staff records garments and generates unique item tags.
- **FR4:** System calculates bills including itemized pricing and express surcharges.
- **FR5:** Staff updates processing stages (`RECEIVED` → `WASHING` → `IRONING` → `PACKING` → `READY_FOR_DELIVERY` → `DELIVERED`).
- **FR6:** Delivery agent completes pickup and delivery.
- **FR7:** Customer tracks real-time order status.
- **FR8:** Admin views daily production and revenue reports.

---

## 🛡️ Non-Functional Requirements (NFRs)

- **NFR-P1 (Tag Uniqueness):** Each garment tag is globally unique across all orders, enforced via Java `Set` data structures.
- **NFR-P2 (Stage Validation):** Stage progression is validated; skipping or regressing stages triggers domain exceptions.
- **NFR-P3 (Mobile-First Architecture):** Clean, decoupled service boundaries ready for mobile/REST integrations.
- **NFR-P4 (Historical Price Preservation):** Pricing changes to the catalog never alter existing historical orders.

---

## 🧩 Design Patterns & Modern Java Principles

- **Strategy Pattern:** `SurchargeStrategy` with `StandardSurchargeStrategy` (0%) and `ExpressSurchargeStrategy` (+50%).
- **Inheritance & Polymorphism:** `BaseEntity` base class, `User` role hierarchy (`Admin`, `Staff`, `DeliveryAgent`, `Customer`).
- **Custom Exception Hierarchy:** `InvalidStageTransitionException`, `DuplicateTagException`, `OrderNotFoundException`.
- **Java Collections Framework:**
  - `List`: Order registry and service price lists.
  - `Set`: Globally unique garment tags (`HashSet`).
  - `Queue`: FIFO logistics pickup requests queue (`ArrayDeque`).

---

## 🗓️ 7-Day Implementation Roadmap

| Day | Focus Area | Deliverables Accomplished |
| :---: | :--- | :--- |
| **Day 1** | Agile/Scrum & Architecture | 8 Functional Requirements converted to user stories with story points & DoD in [BACKLOG_USER_STORIES.md](docs/BACKLOG_USER_STORIES.md). |
| **Day 2** | Git & Language Fundamentals | `.gitignore`, comprehensive `README.md`, `LaundryConstants` business rules, and 1 week of sample data in arrays. |
| **Day 3** | Control Flow & Maven | Multi-module ready `pom.xml`, console interactive menu with one option per FR (FR1–FR8). |
| **Day 4** | OOP Concepts & Encapsulation | Encapsulated domain model classes (`Customer`, `LaundryOrder`, `OrderItem`). |
| **Day 5** | Inheritance & Polymorphism | `BaseEntity`, User role hierarchy (`Admin`, `Staff`, `DeliveryAgent`, `Customer`), and `SurchargeStrategy` implementations. |
| **Day 6** | Exception Handling | Domain exception package (`InvalidStageTransitionException`, `DuplicateTagException`), business rule enforcement, and robust error recovery. |
| **Day 7** | Collections Framework | Data structures (`List`, `Set`, `Queue`), and fully interactive implementations of FR1, FR2, and FR3. |

---

## 🚀 How to Run the Application

Compile and execute the console app directly using Java:

```powershell
# From project root directory
javac -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp out com.laundry.app.LaundryConsoleApp
```
