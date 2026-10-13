import java.util.Scanner;

public class AtmSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int pin = 1234;
        int balance = 5000;
        int attempts = 0;
        boolean loggedIn = false;

        // 3 PIN attempts
        while (attempts < 3) {
            System.out.print("Enter 4-digit PIN: ");
            int enteredPin = sc.nextInt();
            attempts++;

            if (enteredPin == pin) {
                System.out.println("Login successful!");
                loggedIn = true;
                break;
            } else {
                System.out.println("Wrong PIN. Attempts left: " + (3 - attempts));
            }
        }

        if (!loggedIn) {
            System.out.println("Card blocked. Try again later.");
            sc.close();
            return;
        }

        // Mini-statement array
        String[] statement = new String[5];
        int count = 0;

        int choice = 0;
        do {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini-Statement");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: Rs. " + balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    int dep = sc.nextInt();
                    if (dep > 0) {
                        balance += dep;
                        System.out.println("Deposited Rs. " + dep);
                        if (count < statement.length) {
                            statement[count++] = "Deposited: Rs. " + dep;
                        }
                    } else {
                        System.out.println("Invalid amount.");
                    }
                    break;

                case 3:
                    System.out.print("Enter withdraw amount: ");
                    int wit = sc.nextInt();
                    if (wit > 0 && wit <= balance) {
                        balance -= wit;
                        System.out.println("Withdrawn Rs. " + wit);
                        if (count < statement.length) {
                            statement[count++] = "Withdrawn: Rs. " + wit;
                        }
                    } else {
                        System.out.println("Insufficient balance or invalid amount.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Mini Statement ---");
                    for (String s : statement) {
                        if (s != null) {
                            System.out.println(s);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using ATM. Please collect your card.");
                    break;
            }

        } while (choice != 5);

        sc.close();
    }
}
