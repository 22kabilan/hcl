# Day 05 - Inheritance & Polymorphism + Git

## Topics Covered
- Abstract class vs Interface
- `@Override` and `super`
- Method overloading (`pay()`, `pay(note)`, `pay(discount)`)
- Runtime polymorphism
- Git branches, merge conflict, and rebase

## Hands-on Task: Payment System
Created `PaymentSystem.java`:
- Abstract class `Payment`
- Interface `Refundable`
- Subclasses: `CardPayment`, `UPIPayment`, `CashPayment`
- Method overloading for `pay()`
- Polymorphism and interface refund implementation

## How to Run
```bash
javac PaymentSystem.java
java PaymentSystem
```
