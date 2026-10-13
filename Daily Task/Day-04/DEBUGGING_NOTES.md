# Day 04 - Debugging Notes

## Bug in `withdraw()`
Before fixing, the code was subtracting without checking balance:
```java
balance -= amount; // Bug: allowed balance to go below 0
```

## How Debugger was Used
1. Put a **Breakpoint** on `withdraw()` method.
2. Added **Watch** for `balance - amount` to see what balance would become.
3. Added **Conditional Breakpoint** with condition: `amount > balance`.
4. The debugger stopped only when withdrawing more money than available.
5. Fixed the code by adding `if (amount > balance)` check.
