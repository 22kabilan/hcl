import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Iterator;

public class LibraryWaitlist {

    // Simple Patron class
    static class Patron implements Comparable<Patron> {
        String name;
        int role; // 1 for Faculty, 2 for Student

        Patron(String name, int role) {
            this.name = name;
            this.role = role;
        }

        // 1 (Faculty) comes before 2 (Student)
        @Override
        public int compareTo(Patron other) {
            if (this.role < other.role) {
                return -1;
            } else if (this.role > other.role) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            String roleName = (role == 1) ? "Faculty" : "Student";
            return name + " (" + roleName + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Library Waitlist Demo ---");

        // 1. ArrayList for book catalogue
        ArrayList<String> catalogue = new ArrayList<>();
        catalogue.add("Java Programming");
        catalogue.add("Data Structures");
        catalogue.add("Web Basics");
        System.out.println("Catalogue: " + catalogue);

        // 2. LinkedHashSet to avoid duplicates
        LinkedHashSet<String> uniqueBooks = new LinkedHashSet<>();
        uniqueBooks.add("Java Programming");
        uniqueBooks.add("Data Structures");
        uniqueBooks.add("Java Programming"); // duplicate, will be ignored
        System.out.println("Unique Books: " + uniqueBooks);

        // 3. ArrayDeque for general waitlist (First-Come, First-Served)
        ArrayDeque<String> waitlist = new ArrayDeque<>();
        waitlist.offer("Alice");
        waitlist.offer("Bob");
        waitlist.offer("Charlie");
        System.out.println("\nNext in line (FIFO): " + waitlist.poll());

        // 4. PriorityQueue for faculty-first requests
        PriorityQueue<Patron> pq = new PriorityQueue<>();
        pq.offer(new Patron("Suresh", 2));  // Student
        pq.offer(new Patron("Dr. Kumar", 1)); // Faculty
        pq.offer(new Patron("Anita", 2));   // Student

        System.out.println("\nFaculty-First Queue:");
        while (!pq.isEmpty()) {
            System.out.println("Served: " + pq.poll());
        }

        // 5. Iterator.remove to remove overdue loans safely
        ArrayList<Integer> overdueDays = new ArrayList<>();
        overdueDays.add(5);
        overdueDays.add(15); // overdue (> 10)
        overdueDays.add(8);
        overdueDays.add(20); // overdue (> 10)

        System.out.println("\nBorrow days before cleanup: " + overdueDays);
        Iterator<Integer> it = overdueDays.iterator();
        while (it.hasNext()) {
            int days = it.next();
            if (days > 10) {
                it.remove(); // safe removal
            }
        }
        System.out.println("Borrow days after cleanup: " + overdueDays);

        // 6. ArrayList vs LinkedList speed check
        System.out.println("\n--- Performance Check (ArrayList vs LinkedList) ---");
        int count = 20000;

        ArrayList<Integer> al = new ArrayList<>();
        LinkedList<Integer> ll = new LinkedList<>();

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            al.add(0, i);
        }
        long alTime = System.currentTimeMillis() - t1;

        long t2 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            ll.add(0, i);
        }
        long llTime = System.currentTimeMillis() - t2;

        System.out.println("Insert 20k items at front - ArrayList: " + alTime + " ms | LinkedList: " + llTime + " ms");
        System.out.println("Index read get(10000)       - ArrayList is instant | LinkedList is slower");
    }
}
