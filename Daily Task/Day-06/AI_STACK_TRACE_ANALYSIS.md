# Day 06 - AI Stack Trace Notes

## Trace 1: InsufficientStockException
```text
InsufficientStockException: Cannot place order due to low stock.
Caused by: java.lang.Exception: Warehouse only has 10 items in stock.
    at OrderProcessor.processOrder(OrderProcessor.java:30)
```
- **Copilot said:** This is a checked exception caused by requesting more quantity than available stock (15 > 10).
- **Check:** Correct! The cause was properly chained and showed the warehouse message.

## Trace 2: InvalidQuantityException
```text
InvalidQuantityException: Quantity must be greater than 0! Given: -2
    at OrderProcessor.processOrder(OrderProcessor.java:24)
```
- **Copilot said:** This is a runtime exception thrown because a negative quantity was entered.
- **Check:** Correct! It is unchecked so Java compiler doesn't force `throws`.
