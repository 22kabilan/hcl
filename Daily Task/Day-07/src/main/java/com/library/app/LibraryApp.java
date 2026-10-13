package com.library.app;

import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.Patron;
import com.library.service.CollectionPerformanceBenchmark;
import com.library.service.LibrarySystem;

/**
 * LibraryApp
 * Demonstrates:
 * 1. ArrayList catalogue
 * 2. LinkedHashSet ISBN de-duplication
 * 3. ArrayDeque standard waitlist
 * 4. PriorityQueue faculty-first scheduling
 * 5. Iterator.remove() for overdue book purge
 * 6. Running ArrayList vs LinkedList microbenchmark
 */
public class LibraryApp {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     DAY 07: COLLECTIONS FRAMEWORK WAITLIST       ");
        System.out.println("==================================================");

        LibrarySystem system = new LibrarySystem();

        // 1. Register Books (ArrayList + LinkedHashSet de-duplication)
        System.out.println("\n--- Step 1: Registering Books & De-duplication ---");
        Book b1 = new Book("978-0134685991", "Effective Java", "Joshua Bloch");
        Book b2 = new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman");
        Book b3 = new Book("978-0132350884", "Clean Code", "Robert C. Martin");
        Book duplicate = new Book("978-0134685991", "Effective Java (Second Copy)", "Joshua Bloch");

        system.registerBook(b1);
        system.registerBook(b2);
        system.registerBook(b3);
        system.registerBook(duplicate); // Duplicate ISBN: Rejected cleanly by LinkedHashSet

        // 2. Queueing patrons (ArrayDeque FIFO vs PriorityQueue Faculty-First)
        System.out.println("\n--- Step 2: Queueing Patrons ---");
        Patron student1 = new Patron("P-101", "Aarav (Student)", Patron.PatronType.STUDENT);
        Patron student2 = new Patron("P-102", "Diya (Student)", Patron.PatronType.STUDENT);
        Patron faculty1 = new Patron("P-201", "Dr. Raman (Faculty)", Patron.PatronType.FACULTY);
        Patron faculty2 = new Patron("P-202", "Prof. Sen (Faculty)", Patron.PatronType.FACULTY);

        // Standard FIFO Waitlist (ArrayDeque)
        system.enqueueGeneralWaitlist(student1);
        system.enqueueGeneralWaitlist(faculty1);
        system.enqueueGeneralWaitlist(student2);

        // Priority Queue (PriorityQueue - Faculty first)
        system.enqueuePriorityWaitlist(student1);
        system.enqueuePriorityWaitlist(student2);
        system.enqueuePriorityWaitlist(faculty1);
        system.enqueuePriorityWaitlist(faculty2);

        System.out.println("\n--- Step 3: Serving Priority Waitlist (Faculty First) ---");
        Patron next;
        int rank = 1;
        while ((next = system.serveNextPriorityPatron()) != null) {
            System.out.printf("  Serving %d: %-25s [Type: %s]%n", rank++, next.getName(), next.getType());
        }

        // 4. Safe Iterator.remove() for Overdue records
        System.out.println("\n--- Step 4: Iterator.remove() Overdue Purge ---");
        system.recordBorrow(new BorrowRecord("LN-1001", b1, student1, 10)); // Not overdue
        system.recordBorrow(new BorrowRecord("LN-1002", b2, student2, 25)); // Overdue (student limit 14)
        system.recordBorrow(new BorrowRecord("LN-1003", b3, faculty1, 15)); // Not overdue (faculty limit 30)
        system.recordBorrow(new BorrowRecord("LN-1004", b1, faculty2, 45)); // Overdue (faculty limit 30)

        System.out.println("Active Borrows before purge: " + system.getActiveBorrows().size());
        system.purgeOverdueBorrows();
        System.out.println("Active Borrows after purge : " + system.getActiveBorrows().size());

        // 5. Benchmark Execution
        System.out.println("\n--- Step 5: Executing Performance Benchmark ---");
        CollectionPerformanceBenchmark.runBenchmark();
    }
}
