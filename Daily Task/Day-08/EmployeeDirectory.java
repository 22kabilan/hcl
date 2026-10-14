import java.util.*;

// Simple Employee class
class Employee implements Comparable<Employee> {
    int id;
    String name;
    String dept;
    double salary;

    public Employee(int id, String name, String dept, double salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    // Sort by ID in ascending order (101, 102, 103...)
    @Override
    public int compareTo(Employee other) {
        return this.id - other.id;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + dept + ") - Rs. " + salary;
    }
}

// Key class WITHOUT hashCode() to show the bug
class EmployeeIdBad {
    int id;

    public EmployeeIdBad(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmployeeIdBad) {
            EmployeeIdBad other = (EmployeeIdBad) obj;
            return this.id == other.id;
        }
        return false;
    }
    // Note: No hashCode() method here!
}

// Key class WITH hashCode() to show the fix
class EmployeeIdGood {
    int id;

    public EmployeeIdGood(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmployeeIdGood) {
            EmployeeIdGood other = (EmployeeIdGood) obj;
            return this.id == other.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}

public class EmployeeDirectory {
    public static void main(String[] args) {
        System.out.println("--- Employee Directory Demo ---");

        // Sample list of employees
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(105, "Ananya", "IT", 85000));
        list.add(new Employee(102, "Karthik", "HR", 55000));
        list.add(new Employee(108, "Deepak", "Finance", 92000));
        list.add(new Employee(101, "Suresh", "IT", 60000));
        list.add(new Employee(104, "Pooja", "IT", 92000));
        list.add(new Employee(107, "Vikram", "Finance", 75000));
        list.add(new Employee(103, "Meena", "HR", 65000));

        // 1. HashMap lookup by ID
        System.out.println("\n1. HashMap Lookup by ID:");
        HashMap<Integer, Employee> empMap = new HashMap<>();
        for (Employee e : list) {
            empMap.put(e.id, e);
        }
        System.out.println("Found Employee with ID 104: " + empMap.get(104));

        // 2. Frequency count with merge()
        System.out.println("\n2. Department Count using merge():");
        HashMap<String, Integer> deptCounts = new HashMap<>();
        for (Employee e : list) {
            deptCounts.merge(e.dept, 1, Integer::sum);
        }
        for (String dept : deptCounts.keySet()) {
            System.out.println("  " + dept + ": " + deptCounts.get(dept) + " employees");
        }

        // 3. TreeMap department report (alphabetical order)
        System.out.println("\n3. TreeMap Department Report (Sorted by Department):");
        TreeMap<String, ArrayList<Employee>> deptReport = new TreeMap<>();
        for (Employee e : list) {
            if (!deptReport.containsKey(e.dept)) {
                deptReport.put(e.dept, new ArrayList<>());
            }
            deptReport.get(e.dept).add(e);
        }

        for (String dept : deptReport.keySet()) {
            System.out.println("Department: " + dept);
            for (Employee e : deptReport.get(dept)) {
                System.out.println("  -> " + e.id + " " + e.name + " (Rs. " + e.salary + ")");
            }
        }

        // 4. Comparable by ID (Natural order)
        System.out.println("\n4. Sorted by ID (using Comparable):");
        Collections.sort(list); // calls compareTo()
        for (Employee e : list) {
            System.out.println("  " + e);
        }

        // 5. Top-5 salary using Comparator chain
        System.out.println("\n5. Top-5 Employees by Highest Salary:");
        Comparator<Employee> bySalaryThenName = new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                // Descending by salary
                if (e2.salary != e1.salary) {
                    return Double.compare(e2.salary, e1.salary);
                }
                // If salary is same, sort by name
                return e1.name.compareTo(e2.name);
            }
        };

        list.sort(bySalaryThenName);
        for (int i = 0; i < 5; i++) {
            System.out.println("  " + (i + 1) + ". " + list.get(i));
        }

        // 6. Missing hashCode bug demo and fix
        System.out.println("\n6. Missing hashCode Bug Demo and Fix:");

        // Bug demo:
        HashMap<EmployeeIdBad, String> badMap = new HashMap<>();
        badMap.put(new EmployeeIdBad(101), "Suresh");
        System.out.println("[BUG] Without hashCode(), get(101) is: " + badMap.get(new EmployeeIdBad(101)) + " (returned null!)");

        // Fix demo:
        HashMap<EmployeeIdGood, String> goodMap = new HashMap<>();
        goodMap.put(new EmployeeIdGood(101), "Suresh");
        System.out.println("[FIX] With hashCode(), get(101) is   : " + goodMap.get(new EmployeeIdGood(101)) + " (found successfully!)");
    }
}
