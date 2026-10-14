# Day 08 - Collections Framework (Part 2)

## Topics Covered
- `Map` interface: `HashMap` vs `TreeMap`
- Key lookup and frequency counting using `Map.merge()`
- Sorting by natural order with `Comparable<T>`
- Multi-field sorting using `Comparator` chaining (`reversed()`, `thenComparing()`)
- The `equals()` and `hashCode()` contract, and why missing `hashCode()` breaks `HashMap` lookups

---

## Hands-on Task: Employee Directory

Implemented in `EmployeeDirectory.java`:

1. **HashMap Lookup:**
   - Stores employees by `id` for fast $O(1)$ lookup (`employeeMap.get(104)`).
2. **Frequency Count with `merge()`:**
   - Calculates employee count per department in a single line:
     ```java
     deptCounts.merge(emp.department, 1, Integer::sum);
     ```
3. **TreeMap Department Report:**
   - Uses `TreeMap<String, List<Employee>>` to automatically sort departments in alphabetical order.
4. **`Comparable` by ID:**
   - `Employee` implements `Comparable<Employee>` with `compareTo()` to sort by employee ID ascending.
5. **Top-5 Salary `Comparator` Chain:**
   - Chains comparators: sorts by highest salary first (`reversed()`), breaking ties alphabetically by name:
     ```java
     Comparator.comparingDouble((Employee e) -> e.salary).reversed().thenComparing(e -> e.name)
     ```
6. **Missing-`hashCode` Bug Demo and Fix:**
   - **Bug:** `EmployeeKeyBad` implements only `equals()`. Looking up `new EmployeeKeyBad(101)` returns `null` because different memory hashes land in different HashMap buckets.
   - **Fix:** `EmployeeKeyGood` implements both `equals()` and `hashCode()`, correctly retrieving the value.

---

## How to Run

```bash
cd "Day-08"
javac EmployeeDirectory.java
java EmployeeDirectory
```
