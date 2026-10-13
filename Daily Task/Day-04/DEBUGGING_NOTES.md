# Day 04 - IDE Debugging Walkthrough & Planted Bug Notes

## 1. Planted Bug Analysis

### The Planted Bug in `withdraw(double amount)`:
```java
// BUGGY IMPLEMENTATION:
public boolean withdraw(double amount) {
    // Missing check: amount > this.balance was omitted or inverted!
    this.balance -= amount; // Allows balance to drop below zero into unbounded overdraft
    return true;
}
```

### Symptom:
When transferring ₹20,000 from an account holding only ₹15,000, the operation succeeded and reported a negative balance (`-₹5,000.00`) without raising any warning or error.

---

## 2. Debugger Investigation Workflow

### Step 1: Standard Breakpoint
- Set a line breakpoint at the entry of `BankAccount.withdraw(double amount)`.
- Triggered transaction from `BankApp.main()`.
- Thread suspended at the first statement inside `withdraw()`.

### Step 2: Conditional Breakpoint
- Instead of stopping at every routine withdrawal, configured a **Conditional Breakpoint**:
  ```text
  Condition: amount > this.balance
  ```
- **Result:** The debugger paused execution ONLY when an overdraft violation occurred (`amount = 20000.0`, `this.balance = 15000.0`), filtering out valid transactions automatically.

### Step 3: Logpoint
- Configured a non-suspending **Logpoint**:
  ```text
  Message: "Account: {this.accountNumber} | Withdrawing: {amount} | Balance before: {this.balance}"
  ```
- Executed smoothly, emitting telemetry directly to the debug console without stopping thread execution.

### Step 4: Watch Variables & Expressions
- Added watches in IDE Watches panel:
  - `this.balance` → evaluated to `15000.0`
  - `amount` → evaluated to `20000.0`
  - `this.balance - amount` → evaluated to `-5000.0` (revealed negative balance anomaly)
  - `amount > this.balance` → evaluated to `true`

### Step 5: Call Stack Inspection
- Explored call frames:
  1. `BankAccount.withdraw(double)` (Line 95) - active frame
  2. `BankService.transfer(BankAccount, BankAccount, double)` (Line 23)
  3. `BankApp.main(String[])` (Line 42)
- Inspected caller parameters in `BankService` to verify data passed across layer boundaries.

### Step 6: Hot Code Replace (Fix Verification)
- Modified `BankAccount.java` during suspended state:
  ```java
  if (amount > this.balance) {
      System.out.printf(">> [Withdrawal Failed] Insufficient balance for %s! Requested: ₹%.2f, Available: ₹%.2f%n",
              this.accountNumber, amount, this.balance);
      return false;
  }
  ```
- Applied JVM Hot Code Replace / Hot Reload.
- Dropped frame back to `BankService.transfer()` and re-stepped.
- Verified that withdrawal failed gracefully and balance was preserved at ₹15,000.00.
