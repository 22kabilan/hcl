import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Iterator;

public class LibraryWaitlist {

    // Simple Patron class that compares priority (1 = faculty, 2 = student)
    static class Patron implements Comparable<Patron> {
        String name;
        int priority; // 1 for Faculty, 2 for Student

        Patron(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Patron other) {
            return this.priority - other.priority; // smaller number comes first
        }

        @Override
        public String toString() {
            return name + (priority == 1 ? " (Faculty)" : " (Student)");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Library Waitlist Demo ---");

        // 1. ArrayList for book catalogue (easy to read by index)
        ArrayList<String> catalogue = new ArrayList<>();
        catalogue.add("Java Programming");
        catalogue.add("Data Structures");
        catalogue.add("Web Basics");
        System.out.println("Catalogue: " + catalogue);

        // 2. LinkedHashSet for duplicate prevention (no duplicate book titles)
        LinkedHashSet<String> uniqueBooks = new LinkedHashSet<>();
        uniqueBooks.add("Java Programming");
        uniqueBooks.add("Data Structures");
        uniqueBooks.add("Java Programming"); // Duplicate, ignored
        System.out.println("Unique Books: " + uniqueBooks);

        // 3. ArrayDeque for general waitlist (First-In-First-Out)
        ArrayDeque<String> waitlist = new ArrayDeque<>();
        waitlist.offer("Alice");
        waitlist.offer("Bob");
        waitlist.offer("Charlie");
        System.out.println("\nNext from Waitlist (FIFO): " + waitlist.poll());

        // 4. PriorityQueue for faculty-first requests
        PriorityQueue<Patron> pq = new PriorityQueue<>();
        pq.offer(new Patron("Suresh", 2));  // Student
        pq.offer(new Patron("Dr. Kumar", 1)); // Faculty
        pq.offer(new Patron("Anita", 2));   // Student

        System.out.println("\nFaculty-First Queue:");
        while (!pq.isEmpty()) {
            System.out.println("Served: " + pq.poll());
        }

        // 5. Iterator.remove for removing overdue items safely
        ArrayList<Integer> overdueDays = new ArrayList<>();
        overdueDays.add(5);
        overdueDays.add(15); // overdue (> 10)
        overdueDays.add(8);
        overdueDays.add(20); // overdue (> 10)

        System.out.println("\nBorrow days before cleaning: " + overdueDays);
        Iterator<Integer> it = overdueDays.iterator();
        while (it.hasNext()) {
            int days = it.next();
            if (days > 10) {
                it.remove(); // safe remove without ConcurrentModificationException
            }
        }
        System.out.println("Borrow days after removing overdue: " + overdueDays);

        // 6. Timing comparison: ArrayList vs LinkedList
        System.out.println("\n--- Performance Check (ArrayList vs LinkedList) ---");
        int count = 20000;

        ArrayList<Integer> al = new ArrayList<>();
        LinkedList<Integer> ll = new LinkedList<>();

        // Insert at beginning add(0, i)
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) al.add(0, i);
        long alInsertTime = System.currentTimeMillis() - t1;

        t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) ll.add(0, i);
        long llInsertTime = System.currentTimeMillis() - t1;

        System.out.println("Insert 20k items at start - ArrayList: " + alInsertTime + " ms | LinkedList: " + llInsertTime + " ms");
        System.out.println("get(10000) index lookup   - ArrayList is instant (O(1)) | LinkedList is slower (O(n))");
    }
}
