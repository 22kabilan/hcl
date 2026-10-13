# Day 05 - Inheritance & Polymorphism + Git Branching & Merging

## Topics Covered

### Core Inheritance & Polymorphism
- Abstract classes vs Interfaces
- Dynamic method dispatch and runtime polymorphism
- Compile-time polymorphism through method overloading (`pay()`, `pay(note)`, `pay(discount, promo)`)
- Method overriding (`@Override`) and calling `super` constructors
- Decoupling architecture using the Strategy design pattern

### Git Branching & Collaboration
- Feature branch creation and switching
- 3-Way merge vs Fast-forward merge
- Simulating and resolving merge conflicts cleanly
- Rebasing feature branches onto `main`
- Code review via Pull Requests

---

## Daily Hands-on Task: Payment Hierarchy

Implemented a decoupled payment architecture:
- `Refundable` interface: `processRefund(amount, reason)`, `getRefundableAmount()`
- `Payment` abstract class: protected fields, overloaded `pay()` methods, abstract `processPayment()`
- `CardPayment` (implements `Refundable`): card masking, gateway authorization, card refunds
- `UPIPayment` (implements `Refundable`): VPA validation, collect request, UPI refunds
- `CashPayment`: counter cash settlement, change computation
- Standalone runner: `PaymentSystemApp.java`
- Package runner: `com.payment.PaymentApp`

### Compilation & Execution
```bash
# Standalone run:
javac PaymentSystemApp.java
java PaymentSystemApp

# Package run:
javac src/main/java/com/payment/*.java
java -cp src/main/java com.payment.PaymentApp
```

---

## Project Task: Laundry Service Management Roles & Strategy

### 1. Base Entity & Role Hierarchy
- `BaseEntity`: Abstract root entity with audit fields (`id`, `createdAt`, `updatedAt`, `active`).
- `User`: Abstract user extending `BaseEntity` with abstract `getRolePermissionSummary()`.
- Subclasses:
  - `Admin`: Service pricing catalogue and production report access.
  - `Staff`: Item recording, tag generation, and processing stages.
  - `DeliveryAgent`: Customer pickup and delivery logistics.
  - `Customer`: Order placement, stage tracking, and loyalty rewards.

### 2. Strategy Pattern (`com.laundry.strategy`)
- `SurchargeStrategy`: Interface defining `calculateSurcharge(double subtotal)` and `getStrategyName()`.
- `StandardSurchargeStrategy`: Standard 48-hour delivery with 0% surcharge.
- `ExpressSurchargeStrategy`: Priority same-day delivery with configurable 40% surcharge.

### Compilation
```bash
javac src/main/java/com/laundry/model/*.java src/main/java/com/laundry/strategy/*.java
```

---

## Deliverables & Documentation
- **Architecture Diagram:** [`UML_SKETCH.md`](file:///c:/Users/kabio/hcl/Daily%20Task/Day-05/UML_SKETCH.md) (Mermaid diagrams of payment hierarchy, role hierarchy, and strategy pattern).
- **Branching & Conflict Report:** [`GIT_BRANCHING_REPORT.md`](file:///c:/Users/kabio/hcl/Daily%20Task/Day-05/GIT_BRANCHING_REPORT.md) (Step-by-step conflict reproduction, resolution, and rebase commands).

### Acceptance Criteria Checklist
- [x] Code strictly depends on interfaces (`Refundable`, `SurchargeStrategy`).
- [x] Polymorphic array processes multiple payment mechanisms dynamically.
- [x] Overloaded `pay()` methods demonstrate compile-time polymorphism.
- [x] Merge conflict created and resolved cleanly without losing teammate changes.
- [x] UML sketches provided for all class hierarchies.
