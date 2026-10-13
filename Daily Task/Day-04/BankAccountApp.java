import java.util.Objects;

/**
 * BankAccountApp (Day 04 Single-File Standalone Runner)
 * Demonstrates:
 * - BankAccount class with private fields
 * - Static counter
 * - 3 chained constructors
 * - Validated deposit/withdraw
 * - equals/hashCode contracts
 * - Debugging demonstration
 */
public class BankAccountApp {

    static class BankAccount {
        private static int totalAccounts = 1000;

        private final String accountNumber;
        private String accountHolderName;
        private double balance;
        private String accountType;

        // Constructor 1: Default constructor chained to 3-param
        public BankAccount() {
            this("Guest Customer", 0.0, "SAVINGS");
        }

        // Constructor 2: 1-param constructor chained to 3-param
        public BankAccount(String accountHolderName) {
            this(accountHolderName, 1000.0, "SAVINGS");
        }

        // Constructor 3: Primary constructor
        public BankAccount(String accountHolderName, double initialBalance, String accountType) {
            totalAccounts++;
            this.accountNumber = "ACC-" + totalAccounts;
            this.accountHolderName = accountHolderName;
            this.balance = initialBalance;
            this.accountType = accountType;
        }

        public static int getTotalAccounts() {
            return totalAccounts;
        }

        public String getAccountNumber() { return accountNumber; }
        public String getAccountHolderName() { return accountHolderName; }
        public double getBalance() { return balance; }
        public String getAccountType() { return accountType; }

        public void deposit(double amount) {
            if (amount <= 0) {
                System.out.println(">> [Deposit Failed] Amount must be positive.");
                return;
            }
            this.balance += amount;
            System.out.printf(">> [Deposit Success] +₹%.2f to %s. Balance: ₹%.2f%n", amount, accountNumber, balance);
        }

        public boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println(">> [Withdrawal Failed] Amount must be positive.");
                return false;
            }
            // Debugged validation check (preventing overdraw)
            if (amount > this.balance) {
                System.out.printf(">> [Withdrawal Failed] Insufficient balance for %s! Requested: ₹%.2f, Balance: ₹%.2f%n",
                        accountNumber, amount, balance);
                return false;
            }
            this.balance -= amount;
            System.out.printf(">> [Withdrawal Success] -₹%.2f from %s. Balance: ₹%.2f%n", amount, accountNumber, balance);
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

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          DAY 04: BANK ACCOUNT OOP DEMO           ");
        System.out.println("==================================================");

        BankAccount acc1 = new BankAccount();
        BankAccount acc2 = new BankAccount("Alice Smith");
        BankAccount acc3 = new BankAccount("Bob Johnson", 15000.0, "CURRENT");

        System.out.println(acc1);
        System.out.println(acc2);
        System.out.println(acc3);
        System.out.println("Total Accounts Created (Static Counter): " + BankAccount.getTotalAccounts());

        System.out.println("\nTesting Deposit and Withdraw:");
        acc2.deposit(2000.0);
        acc3.withdraw(5000.0);
        acc3.withdraw(25000.0); // Insufficient funds

        System.out.println("\nTesting equals() and hashCode():");
        System.out.println("acc1.equals(acc2) : " + acc1.equals(acc2));
        System.out.println("acc1.equals(acc1) : " + acc1.equals(acc1));
        System.out.println("acc1 hashCode     : " + acc1.hashCode());
    }
}
