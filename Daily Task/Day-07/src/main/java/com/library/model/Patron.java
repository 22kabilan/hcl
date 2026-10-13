package com.library.model;

/**
 * Patron Model
 * Implements Comparable to enable PriorityQueue faculty-first scheduling.
 */
public class Patron implements Comparable<Patron> {

    public enum PatronType {
        FACULTY(1),
        STUDENT(2);

        private final int priority;

        PatronType(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    private final String id;
    private final String name;
    private final PatronType type;

    public Patron(String id, String name, PatronType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PatronType getType() {
        return type;
    }

    @Override
    public int compareTo(Patron other) {
        // Lower priority number = Higher precedence (Faculty priority 1 < Student priority 2)
        int priorityComparison = Integer.compare(this.type.getPriority(), other.type.getPriority());
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format("Patron[ID=%s, Name='%s', Type=%s]", id, name, type);
    }
}
