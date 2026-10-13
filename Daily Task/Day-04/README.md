# Day 04 - OOP Concepts + IDE & Debugging

## Topics Covered

### Core Object-Oriented Programming (OOP)
- Classes, fields, methods, and encapsulation
- Private access modifiers and protected internal state
- Constructor overloading and constructor chaining using `this(...)`
- Static vs instance members (static account counter)
- Implementing `equals()` and `hashCode()` contract based on business identity
- Meaningful `toString()` representations

### IDE Debugging Tools
- Breakpoints and execution suspension
- Conditional breakpoints (e.g. `amount > this.balance`)
- Non-suspending Logpoints for production telemetry simulation
- Watch variables: inspecting compound expressions dynamically
- Call stack traversal across model and service boundaries
- Hot Code Replace: on-the-fly patching of bytecode in the JVM

---

## Daily Task: Bank Account System

Implemented a modular banking architecture across standard packages:
- `com.bank.model.BankAccount`
- `com.bank.service.BankService`
- `com.bank.app.BankApp`
- Standalone runner: `BankAccountApp.java`

### Key Highlights
1. **Strict Encapsulation:**
   - All fields (`accountNumber`, `accountHolderName`, `balance`, `accountType`) are strictly `private`.
   - Mutators enforce validation (no negative balance, no zero deposit).
2. **Static Counter:**
   - `totalAccounts` increments atomically upon instantiation of valid accounts.
3. **Three Chained Constructors (`this(...)`):**
   - `BankAccount()` → `this("Guest Customer", 0.0, "SAVINGS")`
   - `BankAccount(String)` → `this(accountHolderName, 1000.0, "SAVINGS")`
   - `BankAccount(String, double, String)` → Initializes fields, generates `ACC-XXXX`, increments counter.
4. **Planted Bug & Debugging Resolution:**
   - Documented in detail in [`DEBUGGING_NOTES.md`](file:///c:/Users/kabio/hcl/Daily%20Task/Day-04/DEBUGGING_NOTES.md).
   - Diagnosed an overdraft bug using a conditional breakpoint (`amount > this.balance`) and watch expressions (`this.balance - amount`), then fixed via Hot Code Replace.

### Compilation and Execution
```bash
# Standalone run:
javac BankAccountApp.java
java BankAccountApp

# Packaged run:
javac src/main/java/com/bank/model/*.java src/main/java/com/bank/service/*.java src/main/java/com/bank/app/*.java
java -cp src/main/java com.bank.app.BankApp
```

---

## Project Task: Laundry Service Management Domain Models

Created core entity classes in `com.laundry.model`:
1. `Customer.java`: Encapsulates customer identity, contact details, and loyalty points.
2. `LaundryOrder.java`: Manages order lifecycle, items, status, express surcharge calculations, and total bill.
3. `OrderItem.java`: Encapsulates garment type, laundry service type, unit price, quantity, and barcode/RFID tag.

### Compilation
```bash
javac src/main/java/com/laundry/model/*.java
```

### Acceptance Criteria Checklist
- [x] All entity fields are strictly `private`.
- [x] Three constructors chained using `this(...)`.
- [x] Static counter tracks total accounts created.
- [x] `deposit()` and `withdraw()` enforce domain validation rules.
- [x] `equals()` and `hashCode()` correctly implemented based on business identity.
- [x] Debugger workflow documented with conditional breakpoints, watches, and hot code replace in `DEBUGGING_NOTES.md`.
