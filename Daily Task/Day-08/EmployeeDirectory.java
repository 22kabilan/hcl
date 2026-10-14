import java.util.*;

// Employee class
class Employee implements Comparable<Employee> {
    int id;
    String name;
    String department;
    double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Natural ordering: Comparable by id (ascending)
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    // equals based on id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee emp = (Employee) obj;
        return this.id == emp.id;
    }

    // hashCode based on id (essential for HashMap keys!)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Employee[ID=%d, Name='%s', Dept='%s', Salary=Rs. %.2f]", id, name, department, salary);
    }
}

// Key class WITHOUT hashCode() to demonstrate the missing-hashCode bug
class EmployeeKeyBad {
    int id;

    public EmployeeKeyBad(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeKeyBad other = (EmployeeKeyBad) obj;
        return this.id == other.id;
    }
    // Note: hashCode() is NOT overridden here on purpose!
}

// Key class WITH hashCode() implemented to demonstrate the fix
class EmployeeKeyGood {
    int id;

    public EmployeeKeyGood(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeKeyGood other = (EmployeeKeyGood) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class EmployeeDirectory {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("         DAY 08: EMPLOYEE DIRECTORY DEMO          ");
        System.out.println("==================================================");

        // 1. Sample Data
        List<Employee> employees = Arrays.asList(
                new Employee(105, "Ananya", "IT", 85000),
                new Employee(102, "Karthik", "HR", 55000),
                new Employee(108, "Deepak", "Finance", 92000),
                new Employee(101, "Suresh", "IT", 60000),
                new Employee(104, "Pooja", "IT", 92000),
                new Employee(107, "Vikram", "Finance", 75000),
                new Employee(103, "Meena", "HR", 65000)
        );

        // 2. HashMap: Fast O(1) Lookup by Employee ID
        System.out.println("\n--- 1. HashMap Lookup by ID ---");
        Map<Integer, Employee> employeeMap = new HashMap<>();
        for (Employee emp : employees) {
            employeeMap.put(emp.id, emp);
        }

        int searchId = 104;
        Employee found = employeeMap.get(searchId);
        System.out.println("Search for ID " + searchId + ": " + found);

        // 3. Frequency Count with Map.merge()
        System.out.println("\n--- 2. Frequency Count of Employees per Department ---");
        Map<String, Integer> deptCounts = new HashMap<>();
        for (Employee emp : employees) {
            // merge: if department exists, add 1, otherwise set to 1
            deptCounts.merge(emp.department, 1, Integer::sum);
        }
        for (Map.Entry<String, Integer> entry : deptCounts.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " employees");
        }

        // 4. TreeMap: Sorted Department Report
        System.out.println("\n--- 3. TreeMap Department Report (Alphabetical Order) ---");
        Map<String, List<Employee>> deptReport = new TreeMap<>();
        for (Employee emp : employees) {
            deptReport.computeIfAbsent(emp.department, k -> new ArrayList<>()).add(emp);
        }
        for (Map.Entry<String, List<Employee>> entry : deptReport.entrySet()) {
            System.out.println("Department: " + entry.getKey());
            for (Employee emp : entry.getValue()) {
                System.out.println("   -> ID: " + emp.id + ", Name: " + emp.name + ", Salary: Rs. " + emp.salary);
            }
        }

        // 5. Comparable by ID (Natural Ordering)
        System.out.println("\n--- 4. Sorted by ID (Comparable) ---");
        List<Employee> sortedById = new ArrayList<>(employees);
        Collections.sort(sortedById); // uses Employee.compareTo
        for (Employee emp : sortedById) {
            System.out.println("  ID " + emp.id + ": " + emp.name + " (" + emp.department + ")");
        }

        // 6. Comparator Chain: Top-5 by Salary (Descending), then Name
        System.out.println("\n--- 5. Top-5 Employees by Salary (Comparator Chain) ---");
        List<Employee> topSalaryList = new ArrayList<>(employees);
        Comparator<Employee> salaryThenName = Comparator
                .comparingDouble((Employee e) -> e.salary).reversed()
                .thenComparing(e -> e.name);

        topSalaryList.sort(salaryThenName);

        for (int i = 0; i < Math.min(5, topSalaryList.size()); i++) {
            Employee emp = topSalaryList.get(i);
            System.out.printf("  %d. %-10s | Rs. %,10.2f | Dept: %s%n", (i + 1), emp.name, emp.salary, emp.department);
        }

        // 7. Missing-hashCode Bug Demo and Fix
        System.out.println("\n--- 6. Missing-hashCode Bug Demo and Fix ---");

        // The Bug:
        Map<EmployeeKeyBad, String> badMap = new HashMap<>();
        EmployeeKeyBad badKey1 = new EmployeeKeyBad(101);
        badMap.put(badKey1, "Software Engineer");

        EmployeeKeyBad badKeyLookup = new EmployeeKeyBad(101);
        // equals() is true, but hashCode() is default Object memory address!
        System.out.println("[BUG] badKey1.equals(badKeyLookup) : " + badKey1.equals(badKeyLookup));
        System.out.println("[BUG] badMap.get(badKeyLookup)      : " + badMap.get(badKeyLookup) + " (FAILED! returned null)");

        // The Fix:
        Map<EmployeeKeyGood, String> goodMap = new HashMap<>();
        EmployeeKeyGood goodKey1 = new EmployeeKeyGood(101);
        goodMap.put(goodKey1, "Software Engineer");

        EmployeeKeyGood goodKeyLookup = new EmployeeKeyGood(101);
        System.out.println("[FIX] goodKey1.equals(goodKeyLookup): " + goodKey1.equals(goodKeyLookup));
        System.out.println("[FIX] goodMap.get(goodKeyLookup)    : " + goodMap.get(goodKeyLookup) + " (SUCCESS!)");

        System.out.println("\n==================================================");
        System.out.println("       DAY 08 DEMO COMPLETED SUCCESSFULLY!        ");
        System.out.println("==================================================");
    }
}
