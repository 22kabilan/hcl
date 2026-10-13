# Day 07 - ArrayList vs LinkedList Timing Table

| Operation | Workload | ArrayList | LinkedList | Faster |
| :--- | :--- | :--- | :--- | :--- |
| `add(0, item)` (Insert at front) | 20,000 items | ~30 ms | ~2 ms | **LinkedList** (no shifting) |
| `get(index)` (Read by index) | 5,000 reads | < 1 ms | ~50 ms | **ArrayList** (direct index) |
| `add(item)` (Insert at end) | 50,000 items | ~4 ms | ~5 ms | **ArrayList** |

### Why this happens:
- **ArrayList** uses a normal array inside, so finding an element by index `get(i)` is super fast ($O(1)$).
- **LinkedList** connects elements with links/pointers, so inserting at index 0 `add(0, item)` does not need to shift any array elements ($O(1)$).
