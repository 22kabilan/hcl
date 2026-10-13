# Day 05 - Git Branching and Conflict Resolution

1. Created a branch `feature-payment`:
   ```bash
   git checkout -b feature-payment
   ```
2. Both edited the same line in `Payment.java`:
   - Developer A added card fee: `amount = amount + 10;`
   - Developer B added UPI discount: `amount = amount - 10;`
3. When merging, Git gave a **CONFLICT**:
   ```text
   CONFLICT (content): Merge conflict in Payment.java
   ```
4. Resolved conflict by keeping both options with an if condition.
5. Rebased and merged successfully into main branch:
   ```bash
   git add Payment.java
   git commit -m "Resolved merge conflict cleanly"
   ```
