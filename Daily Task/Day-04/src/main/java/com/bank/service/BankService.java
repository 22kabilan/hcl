package com.bank.service;

import com.bank.model.BankAccount;

/**
 * BankService
 * Provides business logic for account transfers and statement display.
 */
public class BankService {

    public boolean transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (fromAccount == null || toAccount == null) {
            System.out.println(">> [Transfer Error] Accounts cannot be null.");
            return false;
        }

        if (fromAccount.equals(toAccount)) {
            System.out.println(">> [Transfer Error] Cannot transfer to the same account.");
            return false;
        }

        System.out.printf("%nInitiating transfer of ₹%.2f from %s to %s...%n",
                amount, fromAccount.getAccountNumber(), toAccount.getAccountNumber());

        // Withdraw from source account
        boolean debited = fromAccount.withdraw(amount);
        if (debited) {
            // Deposit to destination account
            toAccount.deposit(amount);
            System.out.printf(">> [Transfer Complete] Successfully transferred ₹%.2f.%n", amount);
            return true;
        } else {
            System.out.println(">> [Transfer Failed] Source account could not be debited.");
            return false;
        }
    }

    public void displayAccount(BankAccount account) {
        if (account == null) {
            System.out.println("Account record is null.");
            return;
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Account Holder : " + account.getAccountHolderName());
        System.out.printf("Current Balance: ₹%.2f%n", account.getBalance());
        System.out.println("Account Type   : " + account.getAccountType());
        System.out.println("--------------------------------------------------");
    }
}
