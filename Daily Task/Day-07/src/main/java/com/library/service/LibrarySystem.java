package com.library.service;

import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.Patron;

import java.util.*;

/**
 * LibrarySystem
 * Demonstrates:
 * - ArrayList for indexed catalogue browsing
 * - LinkedHashSet for O(1) de-duplication while preserving discovery order
 * - ArrayDeque for high-performance FIFO standard waitlist
 * - PriorityQueue for natural ordering (Faculty scheduled before Students)
 * - Safe iteration removal using Iterator.remove() preventing ConcurrentModificationException
 *
 * All collection choices are justified in inline comments as required.
 */
public class LibrarySystem {

    /*
     * COLLECTION CHOICE JUSTIFICATION: ArrayList
     * 1. Book catalogue is predominantly read-heavy and queried by index/position.
     * 2. ArrayList provides O(1) random access get(index) and minimal memory overhead compared to LinkedList node objects.
     */
    private final List<Book> catalogue = new ArrayList<>();

    /*
     * COLLECTION CHOICE JUSTIFICATION: LinkedHashSet
     * 1. Guarantees that every book ISBN in the registry is strictly unique (de-duplication).
     * 2. Provides O(1) contains() and add() complexity.
     * 3. Unlike HashSet, LinkedHashSet maintains predictable insertion order for historical cataloguing.
     */
    private final Set<String> uniqueIsbnRegistry = new LinkedHashSet<>();

    /*
     * COLLECTION CHOICE JUSTIFICATION: ArrayDeque
     * 1. Used for general waitlist requiring pure First-In-First-Out (FIFO) queue semantics.
     * 2. ArrayDeque is substantially faster than LinkedList when used as a Queue because it avoids node allocation overhead.
     */
    private final Queue<Patron> generalWaitlist = new ArrayDeque<>();

    /*
     * COLLECTION CHOICE JUSTIFICATION: PriorityQueue
     * 1. Used for reserved reservations where Faculty requests must take precedence over Student requests.
     * 2. Backed by a balanced binary min-heap providing O(log n) insertion and O(1) retrieval of highest priority patron.
     */
    private final PriorityQueue<Patron> priorityWaitlist = new PriorityQueue<>();

    /*
     * COLLECTION CHOICE JUSTIFICATION: ArrayList for active borrows
     * Maintains active borrow loans for audit inspections and periodic purge.
     */
    private final List<BorrowRecord> activeBorrows = new ArrayList<>();

    /**
     * Adds a book to the catalogue if its ISBN is not already registered.
     */
    public boolean registerBook(Book book) {
        if (uniqueIsbnRegistry.contains(book.getIsbn())) {
            System.out.printf(">> [De-dup Rejected] Book with ISBN '%s' is already in the catalogue!%n", book.getIsbn());
            return false;
        }
        uniqueIsbnRegistry.add(book.getIsbn());
        catalogue.add(book);
        System.out.printf(">> [Registered] Added '%s' (ISBN: %s) to catalogue.%n", book.getTitle(), book.getIsbn());
        return true;
    }

    /**
     * Adds patron to the standard FIFO waitlist.
     */
    public void enqueueGeneralWaitlist(Patron patron) {
        generalWaitlist.offer(patron);
        System.out.printf(">> [General Waitlist] Queued %s (FIFO position: %d)%n",
                patron.getName(), generalWaitlist.size());
    }

    /**
     * Adds patron to the priority waitlist (Faculty-first).
     */
    public void enqueuePriorityWaitlist(Patron patron) {
        priorityWaitlist.offer(patron);
        System.out.printf(">> [Priority Waitlist] Queued %s (%s) into Priority Heap%n",
                patron.getName(), patron.getType());
    }

    /**
     * Serves the next patron from the Priority Queue.
     */
    public Patron serveNextPriorityPatron() {
        return priorityWaitlist.poll();
    }

    /**
     * Records a book borrowing loan.
     */
    public void recordBorrow(BorrowRecord record) {
        activeBorrows.add(record);
    }

    /**
     * Demonstrates safe collection modification using Iterator.remove().
     * Purges overdue loans without throwing ConcurrentModificationException.
     */
    public List<BorrowRecord> purgeOverdueBorrows() {
        System.out.println("\n[PURGE AUDIT] Scanning active borrows for overdue records using Iterator.remove()...");
        List<BorrowRecord> purged = new ArrayList<>();

        Iterator<BorrowRecord> iterator = activeBorrows.iterator();
        while (iterator.hasNext()) {
            BorrowRecord record = iterator.next();
            if (record.isOverdue()) {
                System.out.printf(">> Found Overdue Loan: %s (%s, %d days) -> PURGING SAFELY%n",
                        record.getRecordId(), record.getPatron().getName(), record.getDaysBorrowed());
                purged.add(record);
                // Safe removal via Iterator: avoids ConcurrentModificationException
                iterator.remove();
            }
        }
        return purged;
    }

    public List<Book> getCatalogue() {
        return Collections.unmodifiableList(catalogue);
    }

    public List<BorrowRecord> getActiveBorrows() {
        return Collections.unmodifiableList(activeBorrows);
    }
}
