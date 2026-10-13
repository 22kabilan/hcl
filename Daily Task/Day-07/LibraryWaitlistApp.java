import java.util.*;

/**
 * LibraryWaitlistApp (Day 07 Single-File Standalone Runner)
 * Demonstrates:
 * - ArrayList for book catalogue
 * - LinkedHashSet for duplicate prevention
 * - ArrayDeque for standard FIFO waitlist
 * - PriorityQueue for Faculty-first priority scheduling
 * - Iterator.remove() for safe overdue item purging
 * - Timing comparison between ArrayList and LinkedList
 */
public class LibraryWaitlistApp {

    static class Book {
        private final String isbn;
        private final String title;

        public Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return Objects.equals(isbn, book.isbn);
        }

        @Override
        public int hashCode() {
            return Objects.hash(isbn);
        }

        @Override
        public String toString() {
            return "[" + isbn + "] " + title;
        }
    }

    static class Patron implements Comparable<Patron> {
        enum Role {
            FACULTY(1), STUDENT(2);
            private final int priority;
            Role(int priority) { this.priority = priority; }
            public int getPriority() { return priority; }
        }

        private final String name;
        private final Role role;

        public Patron(String name, Role role) {
            this.name = name;
            this.role = role;
        }

        public String getName() { return name; }
        public Role getRole() { return role; }

        @Override
        public int compareTo(Patron o) {
            int comp = Integer.compare(this.role.ordinal(), o.role.ordinal());
            if (comp != 0) return comp;
            return this.name.compareTo(o.name);
        }

        @Override
        public String toString() {
            return name + " (" + role + ")";
        }
    }

    static class LoanRecord {
        private final String bookTitle;
        private final Patron patron;
        private final int days;

        public LoanRecord(String title, Patron p, int days) {
            this.bookTitle = title;
            this.patron = p;
            this.days = days;
        }

        public boolean isOverdue() {
            int limit = (patron.getRole() == Patron.Role.FACULTY) ? 30 : 14;
            return days > limit;
        }

        @Override
        public String toString() {
            return bookTitle + " borrowed by " + patron + " for " + days + " days (Overdue: " + isOverdue() + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     DAY 07: LIBRARY WAITLIST COLLECTIONS         ");
        System.out.println("==================================================");

        // 1. ArrayList Catalogue & LinkedHashSet De-duplication
        List<Book> catalogue = new ArrayList<>();
        Set<String> registeredIsbns = new LinkedHashSet<>();

        Book[] incoming = new Book[]{
                new Book("ISBN-001", "Core Java Volume I"),
                new Book("ISBN-002", "Effective Java"),
                new Book("ISBN-001", "Core Java (Duplicate Copy)")
        };

        System.out.println("\n--- Step 1: Catalogue Registration & De-duplication ---");
        for (Book b : incoming) {
            if (registeredIsbns.add(b.getIsbn())) {
                catalogue.add(b);
                System.out.println(">> Added to catalogue: " + b);
            } else {
                System.out.println(">> Duplicate ISBN rejected: " + b.getIsbn());
            }
        }

        // 2. ArrayDeque (FIFO) vs PriorityQueue (Faculty First)
        System.out.println("\n--- Step 2: Queue Demonstrations ---");
        Patron s1 = new Patron("Rahul", Patron.Role.STUDENT);
        Patron s2 = new Patron("Priya", Patron.Role.STUDENT);
        Patron f1 = new Patron("Dr. Sharma", Patron.Role.FACULTY);

        Queue<Patron> fifoQueue = new ArrayDeque<>();
        fifoQueue.offer(s1);
        fifoQueue.offer(f1);
        fifoQueue.offer(s2);

        System.out.println("FIFO Queue Order (ArrayDeque):");
        while (!fifoQueue.isEmpty()) {
            System.out.println("  " + fifoQueue.poll());
        }

        PriorityQueue<Patron> pq = new PriorityQueue<>();
        pq.offer(s1);
        pq.offer(f1);
        pq.offer(s2);

        System.out.println("\nPriority Queue Order (PriorityQueue - Faculty First):");
        while (!pq.isEmpty()) {
            System.out.println("  " + pq.poll());
        }

        // 3. Safe Iterator.remove() for Overdue records
        System.out.println("\n--- Step 3: Iterator.remove() Purge ---");
        List<LoanRecord> loans = new ArrayList<>();
        loans.add(new LoanRecord("Effective Java", s1, 10)); // Ok
        loans.add(new LoanRecord("Core Java Volume I", s2, 20)); // Overdue (student > 14)
        loans.add(new LoanRecord("Design Patterns", f1, 35)); // Overdue (faculty > 30)

        System.out.println("Loans before purge: " + loans.size());
        Iterator<LoanRecord> it = loans.iterator();
        while (it.hasNext()) {
            LoanRecord rec = it.next();
            if (rec.isOverdue()) {
                System.out.println(">> Purging overdue loan: " + rec);
                it.remove(); // Safe removal without ConcurrentModificationException
            }
        }
        System.out.println("Loans after purge: " + loans.size());

        // 4. Timing Comparison
        System.out.println("\n--- Step 4: ArrayList vs LinkedList Benchmark ---");
        int count = 20000;
        List<Integer> al = new ArrayList<>();
        List<Integer> ll = new LinkedList<>();

        long t1 = System.nanoTime();
        for (int i = 0; i < count; i++) al.add(0, i);
        long alHead = (System.nanoTime() - t1) / 1_000_000;

        t1 = System.nanoTime();
        for (int i = 0; i < count; i++) ll.add(0, i);
        long llHead = (System.nanoTime() - t1) / 1_000_000;

        t1 = System.nanoTime();
        long sumAl = 0;
        for (int i = 0; i < 5000; i++) sumAl += al.get(i * 4);
        long alGet = (System.nanoTime() - t1) / 1_000_000;

        t1 = System.nanoTime();
        long sumLl = 0;
        for (int i = 0; i < 5000; i++) sumLl += ll.get(i * 4);
        long llGet = (System.nanoTime() - t1) / 1_000_000;

        System.out.printf("%-25s | %-12s | %-12s%n", "Operation", "ArrayList", "LinkedList");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-25s | %-10d ms | %-10d ms%n", "add(0, elem) [20k]", alHead, llHead);
        System.out.printf("%-25s | %-10d ms | %-10d ms%n", "get(index) [5k reads]", alGet, llGet);
    }
}
