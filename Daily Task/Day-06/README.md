# Day 06 - Exception Handling + AI-Assisted Debugging

## Topics Covered

### Core Exception Handling Architecture
- Checked exceptions (`Exception`) vs Unchecked exceptions (`RuntimeException`)
- `throw` (instantiating and raising an exception) vs `throws` (method declaration contract)
- Multi-catch syntax (`catch (InsufficientStockException | PaymentFailedException e)`)
- Exception Chaining: preserving lower-level causes through `initCause()` and `super(message, cause)`
- Strict `finally` block semantics for guaranteed audit logging and resource teardown
- Anti-pattern eradication: zero empty catch blocks; complete preservation of stack traces

### AI-Assisted Debugging & Stack Trace Analysis
- Top-down parsing of JVM stack traces
- Identifying root cause origin vs wrapping locations
- Prompting AI Copilot to diagnose exceptions and auditing responses for omissions or inaccuracies

---

## Daily Hands-on Task: Order Processor

Implemented an exception-safe ordering engine across:
- `com.order.exception.InsufficientStockException`: Checked exception with inventory details.
- `com.order.exception.InvalidQuantityException`: Unchecked runtime exception for bad arguments.
- `com.order.exception.PaymentFailedException`: Checked gateway settlement failure.
- `com.order.exception.OrderProcessingException`: Domain wrapper preserving root causes via chaining.
- `com.order.service.OrderProcessor`: Core processor with multi-catch and mandatory `finally` audit.
- `com.order.app.OrderApp`: Comprehensive test suite demonstrating full error recovery.
- Standalone runner: `OrderProcessorApp.java`.

### Compilation & Execution
```bash
# Standalone run:
javac OrderProcessorApp.java
java OrderProcessorApp

# Packaged run:
javac src/main/java/com/order/exception/*.java src/main/java/com/order/service/*.java src/main/java/com/order/app/*.java
java -cp src/main/java com.order.app.OrderApp
```

---

## Project Task: Laundry Service Management Exception Package

Created custom enterprise exceptions in `com.laundry.exception`:
1. `LaundryServiceException`: Base checked domain exception.
2. `DuplicateTagException`: Enforces business rule NFR-P1 (RFID/Barcode garment tags must be unique per item).
3. `InvalidStageTransitionException`: Enforces business rule NFR-P2 (Stage progression must strictly follow order lifecycle).
4. `OrderNotFoundException`: Enforces order query lookup validation.

### Compilation
```bash
javac src/main/java/com/laundry/exception/*.java
```

---

## AI Stack Trace Notes & Deliverables
- **Analysis Document:** [`AI_STACK_TRACE_ANALYSIS.md`](file:///c:/Users/kabio/hcl/Daily%20Task/Day-06/AI_STACK_TRACE_ANALYSIS.md)
  - Detailed audit of 2 realistic stack traces (checked chained vs unchecked runtime).
  - Copilot diagnostic query, response, and verified assessment of accuracy and omissions.

### Acceptance Criteria Checklist
- [x] Zero empty catch blocks across the codebase.
- [x] Original root causes preserved using exception chaining (`Throwable cause`).
- [x] Both checked and unchecked custom exceptions properly designed.
- [x] Multi-catch and `finally` audit ledger verified in production flow.
- [x] AI analysis documented with verification notes.
