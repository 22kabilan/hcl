package com.library.model;

/**
 * BorrowRecord
 * Tracks borrowed book items, patron details, and overdue status.
 */
public class BorrowRecord {

    private final String recordId;
    private final Book book;
    private final Patron patron;
    private int daysBorrowed;
    private final int maxAllowedDays;

    public BorrowRecord(String recordId, Book book, Patron patron, int daysBorrowed) {
        this.recordId = recordId;
        this.book = book;
        this.patron = patron;
        this.daysBorrowed = daysBorrowed;
        this.maxAllowedDays = (patron.getType() == Patron.PatronType.FACULTY) ? 30 : 14;
    }

    public String getRecordId() {
        return recordId;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }

    public boolean isOverdue() {
        return daysBorrowed > maxAllowedDays;
    }

    @Override
    public String toString() {
        return String.format("BorrowRecord[ID=%s, Book='%s', Patron=%s, Days=%d, Overdue=%b]",
                recordId, book.getTitle(), patron.getName(), daysBorrowed, isOverdue());
    }
}
