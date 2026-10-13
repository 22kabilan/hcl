public class BankAccount {
    // static counter
    static int count = 0;

    // private fields
    private int accNo;
    private String name;
    private double balance;

    // Constructor 1: Default
    public BankAccount() {
        this("Unknown", 0.0);
    }

    // Constructor 2: Name only
    public BankAccount(String name) {
        this(name, 500.0);
    }

    // Constructor 3: Name and Balance
    public BankAccount(String name, double balance) {
        count++;
        this.accNo = 1000 + count;
        this.name = name;
        this.balance = balance;
    }

    public int getAccNo() {
        return accNo;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited Rs. " + amount + " to " + name + ". New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // withdraw with validation
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdraw amount.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance for " + name + "! Current: " + balance);
        } else {
            balance -= amount;
            System.out.println("Withdrawn Rs. " + amount + " from " + name + ". New Balance: " + balance);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BankAccount) {
            BankAccount other = (BankAccount) obj;
            return this.accNo == other.accNo;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return accNo;
    }

    public static void main(String[] args) {
        System.out.println("--- Bank Account Demo ---");

        // 3 chained constructors
        BankAccount b1 = new BankAccount();
        BankAccount b2 = new BankAccount("Ravi");
        BankAccount b3 = new BankAccount("Priya", 3000.0);

        System.out.println("Account 1: " + b1.getAccNo() + " - " + b1.getName() + " - Rs. " + b1.getBalance());
        System.out.println("Account 2: " + b2.getAccNo() + " - " + b2.getName() + " - Rs. " + b2.getBalance());
        System.out.println("Account 3: " + b3.getAccNo() + " - " + b3.getName() + " - Rs. " + b3.getBalance());

        System.out.println("Total Accounts created: " + BankAccount.count);

        // deposit and withdraw
        b2.deposit(1000);
        b3.withdraw(1500);
        b3.withdraw(5000); // Insufficient

        // equals check
        System.out.println("b1 equals b2: " + b1.equals(b2));
        System.out.println("b1 equals b1: " + b1.equals(b1));
    }
}
