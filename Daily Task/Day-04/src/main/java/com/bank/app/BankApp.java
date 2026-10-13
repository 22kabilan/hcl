package com.bank.app;

import com.bank.model.BankAccount;
import com.bank.service.BankService;

/**
 * BankApp
 * Demonstrates:
 * - Creating accounts using all 3 chained constructors
 * - Static account counter verification
 * - Encapsulation and data protection
 * - Validated deposit and withdraw
 * - Overridden equals() and hashCode() contracts
 * - Debugging scenario walkthrough
 */
public class BankApp {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     DAY 04: OOP CONCEPTS & BANKING SYSTEM        ");
        System.out.println("==================================================");

        // 1. Chained Constructor Demonstrations
        System.out.println("\n--- Step 1: Chained Constructors Demonstration ---");
        // Constructor 1: Default constructor (chains to 3-param)
        BankAccount acc1 = new BankAccount();
        System.out.println("Account 1 (Default): " + acc1);

        // Constructor 2: 1-parameter constructor (chains to 3-param)
        BankAccount acc2 = new BankAccount("Alice Smith");
        System.out.println("Account 2 (1-Param): " + acc2);

        // Constructor 3: 3-parameter primary constructor
        BankAccount acc3 = new BankAccount("Bob Johnson", 15000.0, "CURRENT");
        System.out.println("Account 3 (3-Param): " + acc3);

        // Verify static counter
        System.out.println("\nTotal Bank Accounts Created (static counter): " + BankAccount.getTotalAccounts());

        // 2. Deposit & Withdraw Operations
        System.out.println("\n--- Step 2: Validated Operations ---");
        acc2.deposit(2500.0);
        acc2.deposit(-500.0); // Invalid deposit

        acc3.withdraw(4000.0); // Valid withdrawal
        acc3.withdraw(20000.0); // Exceeds balance: should fail gracefully

        // 3. Service Level Operations
        System.out.println("\n--- Step 3: Bank Service Transfer ---");
        BankService service = new BankService();
        service.transfer(acc3, acc2, 3000.0);

        System.out.println("\nUpdated Account States:");
        service.displayAccount(acc2);
        service.displayAccount(acc3);

        // 4. Testing equals() and hashCode()
        System.out.println("\n--- Step 4: Equals & HashCode Contract Verification ---");
        BankAccount accDuplicateReference = acc2;
        BankAccount anotherAccount = new BankAccount("Alice Smith", 3500.0, "SAVINGS");

        System.out.println("acc2 == accDuplicateReference : " + (acc2 == accDuplicateReference));
        System.out.println("acc2.equals(accDuplicateRef) : " + acc2.equals(accDuplicateReference));
        System.out.println("acc2.equals(anotherAccount)  : " + acc2.equals(anotherAccount) + " (Distinct account numbers)");
        System.out.println("acc2 hashCode: " + acc2.hashCode());
        System.out.println("anotherAccount hashCode: " + anotherAccount.hashCode());

        System.out.println("\n==================================================");
        System.out.println("     OOP VERIFICATION COMPLETED SUCCESSFULLY      ");
        System.out.println("==================================================");
    }
}
