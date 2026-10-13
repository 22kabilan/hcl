# Day 07 - Collections Framework (Part 1)

## Topics Covered

### Core Collections Framework
- Hierarchy of `Collection`, `List`, `Set`, and `Queue`
- Implementation characteristics: `ArrayList`, `LinkedList`, `ArrayDeque`, `PriorityQueue`, `LinkedHashSet`, `TreeSet`
- Iteration patterns: Enhanced `for` vs `Iterator` vs `ListIterator`
- Diagnosing and avoiding `ConcurrentModificationException` using `Iterator.remove()`
- Priority queues and natural ordering via `Comparable` and `Comparator`
- Algorithmic and memory overhead trade-offs between array-backed and linked-node collections

---

## Daily Hands-on Task: Library Waitlist System

Implemented a full-featured library circulation management system:
- `Book`: Model with ISBN-based `equals()` and `hashCode()`.
- `Patron`: Model implementing `Comparable<Patron>` prioritizing `FACULTY` over `STUDENT`.
- `BorrowRecord`: Loan tracker with role-based loan duration rules (Faculty 30 days, Student 14 days).
- `LibrarySystem`:
  - **`ArrayList<Book>` catalogue:** *Justification: Read-heavy browsing with $O(1)$ indexed access.*
  - **`LinkedHashSet<String>` unique ISBNs:** *Justification: $O(1)$ duplicate prevention preserving insertion order.*
  - **`ArrayDeque<Patron>` general waitlist:** *Justification: High-throughput FIFO queue with zero node overhead.*
  - **`PriorityQueue<Patron>` priority waitlist:** *Justification: Min-heap prioritizing faculty over students.*
  - **Safe Overdue Purge:** Uses `Iterator.remove()` to safely remove overdue loans during active traversal.
- Standalone runner: `LibraryWaitlistApp.java`.
- Packaged runner: `com.library.app.LibraryApp`.

### Compilation & Execution
```bash
# Standalone run:
javac LibraryWaitlistApp.java
java LibraryWaitlistApp

# Packaged run:
javac src/main/java/com/library/model/*.java src/main/java/com/library/service/*.java src/main/java/com/library/app/*.java
java -cp src/main/java com.library.app.LibraryApp
```

---

## Microbenchmark: `ArrayList` vs `LinkedList`

Executed micro-benchmarks comparing `ArrayList` vs `LinkedList`:
- **Detailed Report:** [`BENCHMARK_TIMING_TABLE.md`](file:///c:/Users/kabio/hcl/Daily%20Task/Day-07/BENCHMARK_TIMING_TABLE.md)

### Summary Benchmark Results

| Operation & Workload | `ArrayList` Time | `LinkedList` Time | Winner / Preferred |
| :--- | :--- | :--- | :--- |
| **`add(0, elem)` [25,000 items]** | 44 ms | 3 ms | **LinkedList** (no array shift) |
| **`add(elem)` tail [100,000 items]** | 4 ms | 3 ms | **Both Fast** (amortized $O(1)$) |
| **`get(index)` [10,000 random reads]** | **0 ms** | 1064 ms | **ArrayList** ($O(1)$ direct offset) |

---

## Project Task: Laundry Service FR1–FR3 Collections Integration

Implemented in `com.laundry.service.LaundryCollectionService`:
- **FR1 (Admin Services & Prices):** Stored in `List<ServiceItem>` (`ArrayList`) because pricing catalogues require deterministic ordering and positional indexing.
- **FR2 (Customer Pickup Queue):** Stored in `Queue<PickupRequest>` (`ArrayDeque`) because pickup dispatching operates in strict FIFO order, with higher efficiency than `LinkedList`.
- **FR3 (Garment Tagging & RFID):** Stored in `Set<String>` (`LinkedHashSet`) to strictly enforce business rule NFR-P1 (RFID tags must be unique per garment), preventing duplicates with $O(1)$ lookup.

### Compilation
```bash
javac src/main/java/com/laundry/service/LaundryCollectionService.java
```

### Acceptance Criteria Checklist
- [x] Every collection selection is explicitly justified in inline comments.
- [x] Duplicate items rejected via `Set` contract.
- [x] Priority ordering verified with `PriorityQueue` (Faculty served first).
- [x] Safe element removal demonstrated with `Iterator.remove()`.
- [x] Benchmark executed and documented in `BENCHMARK_TIMING_TABLE.md`.
