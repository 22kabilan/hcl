import java.util.Scanner;

/**
 * ATM Simulator (Day 03 Hands-on Task)
 * Demonstrates:
 * - do-while menu + switch
 * - input validation
 * - 3 PIN attempts with break
 * - continue on invalid entry
 * - enhanced-for mini-statement
 */
public class AtmSimulator {

    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final double DAILY_WITHDRAWAL_LIMIT = 25000.00;
    private static final int MAX_TRANSACTIONS = 10;

    private static String currentPin = "1234";
    private static double balance = 10000.00;
    private static double withdrawnToday = 0.00;

    private static final String[] transactionHistory = new String[MAX_TRANSACTIONS];
    private static int transactionCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("          WELCOME TO SECURE BANK ATM              ");
        System.out.println("==================================================");

        boolean authenticated = false;
        int attempts = 0;

        while (attempts < MAX_PIN_ATTEMPTS) {
            attempts++;
            System.out.print("Enter your 4-digit PIN (Attempt " + attempts + "/" + MAX_PIN_ATTEMPTS + "): ");
            String enteredPin = scanner.nextLine().trim();

            if (enteredPin.equals(currentPin)) {
                authenticated = true;
                System.out.println(">> PIN verified successfully!\n");
                recordTransaction("AUTHENTICATED: Successful card login");
                break;
            } else {
                System.out.println(">> Incorrect PIN.");
                if (attempts == MAX_PIN_ATTEMPTS) {
                    System.out.println(">> 3 incorrect attempts reached. Card blocked for 24 hours!");
                    break;
                }
            }
        }

        if (!authenticated) {
            System.out.println(">> Session terminated. Please contact your branch.");
            scanner.close();
            return;
        }

        int choice = 0;
        do {
            printMenu();
            System.out.print("Choose an option (1-6): ");

            if (!scanner.hasNextInt()) {
                String invalidInput = scanner.nextLine();
                System.out.println(">> Invalid input: '" + invalidInput + "'. Please enter a number between 1 and 6.\n");
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleCheckBalance();
                    break;

                case 2:
                    handleDeposit(scanner);
                    break;

                case 3:
                    handleWithdraw(scanner);
                    break;

                case 4:
                    handleMiniStatement();
                    break;

                case 5:
                    handleChangePin(scanner);
                    break;

                case 6:
                    System.out.println("\n>> Thank you for banking with us. Please collect your card.");
                    break;

                default:
                    System.out.println(">> Invalid choice! Please select an option between 1 and 6.\n");
                    break;
            }

        } while (choice != 6);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("--------------------------------------------------");
        System.out.println("                MAIN ATM MENU                     ");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Cash");
        System.out.println("4. Mini-Statement");
        System.out.println("5. Change PIN");
        System.out.println("6. Exit & Eject Card");
        System.out.println("--------------------------------------------------");
    }

    private static void handleCheckBalance() {
        System.out.println("\n[ACCOUNT BALANCE]");
        System.out.printf("Available Balance : ₹ %.2f%n", balance);
        System.out.printf("Withdrawn Today   : ₹ %.2f / ₹ %.2f%n%n", withdrawnToday, DAILY_WITHDRAWAL_LIMIT);
        recordTransaction(String.format("BALANCE INQUIRY: Current balance ₹%.2f", balance));
    }

    private static void handleDeposit(Scanner scanner) {
        System.out.print("\nEnter amount to deposit (₹): ");
        if (!scanner.hasNextDouble()) {
            String bad = scanner.nextLine();
            System.out.println(">> Deposit failed: '" + bad + "' is not a valid monetary amount.\n");
            return;
        }

        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println(">> Deposit amount must be greater than zero.\n");
            return;
        }

        balance += amount;
        System.out.printf(">> Successfully deposited ₹%.2f. New Balance: ₹%.2f%n%n", amount, balance);
        recordTransaction(String.format("DEPOSIT: +₹%.2f | Balance: ₹%.2f", amount, balance));
    }

    private static void handleWithdraw(Scanner scanner) {
        System.out.print("\nEnter amount to withdraw (₹): ");
        if (!scanner.hasNextDouble()) {
            String bad = scanner.nextLine();
            System.out.println(">> Withdrawal failed: '" + bad + "' is not a valid monetary amount.\n");
            return;
        }

        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println(">> Withdrawal amount must be greater than zero.\n");
            return;
        }

        if (amount > balance) {
            System.out.printf(">> Insufficient funds! Current balance: ₹%.2f%n%n", balance);
            return;
        }

        if ((withdrawnToday + amount) > DAILY_WITHDRAWAL_LIMIT) {
            double remainingLimit = DAILY_WITHDRAWAL_LIMIT - withdrawnToday;
            System.out.printf(">> Daily limit exceeded! Remaining limit today: ₹%.2f%n%n", remainingLimit);
            return;
        }

        balance -= amount;
        withdrawnToday += amount;
        System.out.printf(">> Please collect your cash: ₹%.2f. Remaining Balance: ₹%.2f%n%n", amount, balance);
        recordTransaction(String.format("WITHDRAWAL: -₹%.2f | Balance: ₹%.2f", amount, balance));
    }

    private static void handleMiniStatement() {
        System.out.println("\n==================================================");
        System.out.println("              MINI-STATEMENT (RECENT ACTIVITY)    ");
        System.out.println("==================================================");

        if (transactionCount == 0) {
            System.out.println("No transactions recorded in this session.");
        } else {
            int index = 1;
            for (String tx : transactionHistory) {
                if (tx == null) {
                    break;
                }
                System.out.printf("%2d. %s%n", index++, tx);
            }
        }
        System.out.printf("Current Net Balance: ₹%.2f%n", balance);
        System.out.println("==================================================\n");
    }

    private static void handleChangePin(Scanner scanner) {
        System.out.print("\nEnter your current PIN: ");
        String oldPin = scanner.nextLine().trim();

        if (!oldPin.equals(currentPin)) {
            System.out.println(">> Current PIN does not match. Operation cancelled.\n");
            return;
        }

        System.out.print("Enter new 4-digit PIN: ");
        String newPin = scanner.nextLine().trim();

        if (newPin.length() != 4 || !newPin.matches("\\d{4}")) {
            System.out.println(">> Invalid PIN format! PIN must be exactly 4 numeric digits.\n");
            return;
        }

        if (newPin.equals(oldPin)) {
            System.out.println(">> New PIN cannot be the same as the old PIN.\n");
            return;
        }

        currentPin = newPin;
        System.out.println(">> PIN changed successfully!\n");
        recordTransaction("PIN CHANGE: Customer updated card PIN");
    }

    private static void recordTransaction(String entry) {
        if (transactionCount < MAX_TRANSACTIONS) {
            transactionHistory[transactionCount++] = entry;
        } else {
            System.arraycopy(transactionHistory, 1, transactionHistory, 0, MAX_TRANSACTIONS - 1);
            transactionHistory[MAX_TRANSACTIONS - 1] = entry;
        }
    }
}
