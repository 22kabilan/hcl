package com.bank.model;

import java.util.Objects;

/**
 * BankAccount Model
 * Demonstrates:
 * - Private fields (Encapsulation)
 * - Static account counter
 * - 3 Chained constructors using this(...)
 * - Validated deposit and withdraw
 * - Overridden equals() and hashCode()
 */
public class BankAccount {

    // Static counter shared across all BankAccount instances
    private static int totalAccounts = 1000;

    // Private fields (Strict encapsulation)
    private final String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;

    /**
     * Constructor 1: Default constructor.
     * Chained to 3-parameter constructor using this().
     */
    public BankAccount() {
        this("Guest Customer", 0.0, "SAVINGS");
    }

    /**
     * Constructor 2: Single-parameter constructor.
     * Chained to 3-parameter constructor using this().
     */
    public BankAccount(String accountHolderName) {
        this(accountHolderName, 1000.0, "SAVINGS");
    }

    /**
     * Constructor 3: Primary constructor with full parameters.
     * Generates a unique account number and updates the static counter.
     */
    public BankAccount(String accountHolderName, double initialBalance, String accountType) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        if (initialBalance < 0.0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        totalAccounts++;
        this.accountNumber = "ACC-" + totalAccounts;
        this.accountHolderName = accountHolderName.trim();
        this.balance = initialBalance;
        this.accountType = (accountType != null && !accountType.trim().isEmpty()) ? accountType.toUpperCase() : "SAVINGS";
    }

    // Static counter getter
    public static int getTotalAccounts() {
        return totalAccounts;
    }

    // Encapsulated Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        if (accountHolderName != null && !accountHolderName.trim().isEmpty()) {
            this.accountHolderName = accountHolderName.trim();
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        if (accountType != null && !accountType.trim().isEmpty()) {
            this.accountType = accountType.toUpperCase();
        }
    }

    /**
     * Validated Deposit
     */
    public void deposit(double amount) {
        if (amount <= 0.0) {
            System.out.println(">> [Deposit Failed] Amount must be strictly greater than zero.");
            return;
        }
        this.balance += amount;
        System.out.printf(">> [Deposit Success] Deposited ₹%.2f into %s. New Balance: ₹%.2f%n",
                amount, this.accountNumber, this.balance);
    }

    /**
     * Validated Withdrawal.
     * (Planted bug resolved: previously did not check if balance >= amount, causing negative balances).
     */
    public boolean withdraw(double amount) {
        if (amount <= 0.0) {
            System.out.println(">> [Withdrawal Failed] Amount must be positive.");
            return false;
        }

        // Bug Fixed: Ensure sufficient funds before debiting
        if (amount > this.balance) {
            System.out.printf(">> [Withdrawal Failed] Insufficient balance for %s! Requested: ₹%.2f, Available: ₹%.2f%n",
                    this.accountNumber, amount, this.balance);
            return false;
        }

        this.balance -= amount;
        System.out.printf(">> [Withdrawal Success] Withdrawn ₹%.2f from %s. Remaining Balance: ₹%.2f%n",
                amount, this.accountNumber, this.balance);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("BankAccount[No=%s, Holder='%s', Balance=₹%.2f, Type=%s]",
                accountNumber, accountHolderName, balance, accountType);
    }
}
