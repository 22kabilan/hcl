# Day 05 - Git Branching, Merge Conflict Resolution & Rebase Report

## 1. Branch Strategy Overview
This report demonstrates the workflow practiced for concurrent team development:
- `main`: Production-ready branch.
- `feature/card-surcharge`: Branch adding credit card processing fee.
- `feature/upi-discount`: Branch adding UPI instant cashback rule.

Both feature branches modified the payment calculation logic in `Payment.java`, producing an intentional merge conflict upon integration.

---

## 2. Command Progression & Conflict Simulation

### Step 1: Create and Work on Feature Branch 1
```bash
git checkout -b feature/card-surcharge
# Edited Payment.java to add 1.5% gateway surcharge on card payments
git commit -am "feat(payment): add 1.5% gateway fee calculation"
git push origin feature/card-surcharge
```

### Step 2: Merge Feature 1 into Main
```bash
git checkout main
git merge --no-ff feature/card-surcharge -m "Merge pull request #1 from feature/card-surcharge"
git push origin main
```

### Step 3: Work on Feature Branch 2 (Branching from earlier base)
```bash
git checkout -b feature/upi-discount
# Edited the identical line in Payment.java to add ₹25 flat discount on UPI
git commit -am "feat(payment): add flat ₹25 UPI introductory discount"
```

### Step 4: Rebase feature/upi-discount onto updated main
```bash
git checkout feature/upi-discount
git rebase main
```

**Git Output (Conflict Triggered):**
```text
Auto-merging src/main/java/com/payment/Payment.java
CONFLICT (content): Merge conflict in src/main/java/com/payment/Payment.java
error: could not apply 7b4e91... feat(payment): add flat ₹25 UPI introductory discount
hint: Resolve all conflicts manually, mark them as resolved with
hint: "git add/rm <conflicted_files>", then run "git rebase --continue".
```

---

## 3. Conflict Resolution

### Conflicted Block in `Payment.java`:
```java
<<<<<<< HEAD (main)
        // Card Surcharge Rule
        double finalAmount = amount + (amount * 0.015);
=======
        // UPI Discount Rule
        double finalAmount = Math.max(0.0, amount - 25.0);
>>>>>>> 7b4e91 (feat(payment): add flat ₹25 UPI introductory discount)
```

### Clean Resolution Applied:
Both rules are business-valid and harmonized cleanly based on payment method type:
```java
        // Harmonized Resolution: Apply rule based on payment method
        double finalAmount = amount;
        if (this instanceof CardPayment) {
            finalAmount += (amount * 0.015); // 1.5% card convenience fee
        } else if (this instanceof UPIPayment) {
            finalAmount = Math.max(0.0, amount - 25.0); // ₹25 UPI discount
        }
```

### Finalizing Rebase & Push:
```bash
git add src/main/java/com/payment/Payment.java
git rebase --continue
# Successfully rebased and updated refs/heads/feature/upi-discount.
git push origin feature/upi-discount --force-with-lease
```

---

## 4. Key Takeaways
- **Rebase vs 3-Way Merge:** `git rebase` creates a clean, linear commit history by replaying feature commits on top of `main`.
- **Conflict Hygiene:** Conflicts must never be resolved by simply discarding a teammate's changes; intent from both features was preserved.
- **Interface Decoupling:** Code cleanly depends on the `Refundable` interface and `SurchargeStrategy` abstraction rather than concrete implementations.
