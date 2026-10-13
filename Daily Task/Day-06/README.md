# Day 06 - Exception Handling + AI Debugging

## Topics Covered
- `try`, `catch`, `finally`
- `throw` vs `throws`
- Checked vs unchecked exceptions
- Custom exceptions and exception chaining (`getCause()`)
- Stack trace reading

## Hands-on Task: Order Processor
Created `OrderProcessor.java`:
- Custom checked `InsufficientStockException`
- Custom unchecked `InvalidQuantityException`
- Exception chaining to keep the original reason
- Multi-catch handling
- `finally` block for audit check

## How to Run
```bash
javac OrderProcessor.java
java OrderProcessor
```
