// Interface
interface Refundable {
    void refund(double amount);
}

// Abstract Class
abstract class Payment {
    double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    // Abstract method
    abstract void pay();

    // Overloaded methods
    void pay(String note) {
        System.out.println("Payment Note: " + note);
        pay();
    }

    void pay(double discount) {
        this.amount -= discount;
        System.out.println("Discount Rs. " + discount + " applied!");
        pay();
    }
}

// Subclass 1: Card
class CardPayment extends Payment implements Refundable {
    String cardNumber;

    CardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    void pay() {
        System.out.println("Paid Rs. " + amount + " using Card ending with " + cardNumber.substring(cardNumber.length() - 4));
    }

    @Override
    public void refund(double refundAmount) {
        System.out.println("Refunded Rs. " + refundAmount + " back to Card.");
    }
}

// Subclass 2: UPI
class UPIPayment extends Payment implements Refundable {
    String upiId;

    UPIPayment(double amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    @Override
    void pay() {
        System.out.println("Paid Rs. " + amount + " using UPI (" + upiId + ")");
    }

    @Override
    public void refund(double refundAmount) {
        System.out.println("Refunded Rs. " + refundAmount + " back to UPI.");
    }
}

// Subclass 3: Cash
class CashPayment extends Payment {
    CashPayment(double amount) {
        super(amount);
    }

    @Override
    void pay() {
        System.out.println("Paid Rs. " + amount + " in Cash.");
    }
}

// Main class
public class PaymentSystem {
    public static void main(String[] args) {
        System.out.println("--- Payment System Demo ---");

        // Polymorphism: using parent class reference
        Payment p1 = new CardPayment(1500, "1234567890123456");
        Payment p2 = new UPIPayment(500, "user@upi");
        Payment p3 = new CashPayment(200);

        // Calling pay()
        p1.pay();
        p2.pay("Laundry bill");
        p3.pay(20.0); // with discount

        System.out.println("\n--- Refund Demo ---");
        // Using Refundable interface
        if (p1 instanceof Refundable) {
            ((Refundable) p1).refund(300);
        }

        if (p2 instanceof Refundable) {
            ((Refundable) p2).refund(100);
        }
    }
}
