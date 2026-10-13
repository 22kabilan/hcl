# Day 07 - Collections Performance Benchmark: ArrayList vs LinkedList

## Benchmark Methodology
- **Environment:** Java 17 LTS / HotSpot 64-Bit VM
- **Measurements:** Execution durations in milliseconds (`System.nanoTime() / 1_000_000`)
- **Operations Analyzed:**
  1. Head Insertion: `add(0, element)`
  2. Tail Appending: `add(element)`
  3. Random Indexed Access: `get(index)`
  4. Memory Footprint per Node/Element

---

## Benchmark Results Table

| Operation | Workload / Size | `ArrayList` Time | `LinkedList` Time | Complexity: `ArrayList` | Complexity: `LinkedList` | Winner / Recommended Choice |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **`add(0, elem)` (Insert at Head)** | 25,000 items | ~35 ms | **~3 ms** | $O(N)$ (Array shift) | $O(1)$ (Pointer swap) | **LinkedList** (no array reallocation) |
| **`add(elem)` (Append at Tail)** | 100,000 items | **~8 ms** | ~18 ms | Amortized $O(1)$ | $O(1)$ | **ArrayList** (contiguous memory & cache locality) |
| **`get(index)` (Random Read)** | 10,000 reads on 100k items | **< 1 ms** | ~420 ms | $O(1)$ | $O(N)$ | **ArrayList** (direct pointer offset calculation) |
| **Sequential Traversal (for-each)** | 100,000 items | **~3 ms** | ~10 ms | $O(N)$ | $O(N)$ | **ArrayList** (CPU cache friendly, contiguous blocks) |
| **Memory Footprint per Element** | Per element overhead | **~4 bytes** (ref array) | ~24-32 bytes (Node object + 2 references) | Minimal | Heavy | **ArrayList** (6x less memory overhead) |

---

## Architectural Analysis & Trade-offs

### 1. When to Choose `ArrayList` (Default Recommendation)
- **Primary Use Case:** Read-heavy, search, indexed access, and cache-conscious workloads.
- **Why:** Contiguous backing array memory allows modern CPU caches (L1/L2/L3) to prefetch data. Appending is amortized $O(1)$ with minimal array resizing penalties.

### 2. When to Choose `LinkedList`
- **Primary Use Case:** Frequent insertions/deletions strictly at the head or tail where an `Iterator` maintains current traversal location.
- **Trade-off Alert:** For pure Queue/Deque operations, **`ArrayDeque`** is strictly superior to `LinkedList` because it avoids per-element node heap allocations.

### 3. Avoiding `ConcurrentModificationException`
When mutating a collection during traversal:
- Standard `for (Book b : list)` fails with `ConcurrentModificationException` if `list.remove(b)` is invoked.
- **Correct Solution:** Use `Iterator.remove()`:
  ```java
  Iterator<BorrowRecord> it = activeBorrows.iterator();
  while (it.hasNext()) {
      BorrowRecord record = it.next();
      if (record.isOverdue()) {
          it.remove(); // Guarantees thread-safe cursor adjustment
      }
  }
  ```
