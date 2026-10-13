# Day 03 - Control Flow + Maven

## Topics Covered

### Java Control Flow
- `if-else` branching vs `switch` multi-way selection
- `while` and `do-while` loops
- Standard `for` and enhanced `for` loops
- Control jump statements: `break`, `continue`, labelled breaks
- Input validation and exception-free interactive menus

### Maven Fundamentals
- Standard Maven directory layout (`src/main/java`, `pom.xml`)
- Maven coordinates: `groupId`, `artifactId`, `version`, and packaging
- Maven build lifecycle phases: `validate` → `compile` → `test` → `package` → `install`
- Environment profiles: `dev` and `prod` with dynamic properties
- Dependency management and compiler plugin configuration

---

## Daily Hands-on Task: ATM Simulator

Developed an interactive, robust ATM Simulator with full defensive input validation.

### Key Implementation Details
1. **PIN Authentication with 3-Attempt Lockout (`break`):**
   - Allows up to 3 PIN verification attempts.
   - On successful entry (`"1234"`), exits the loop immediately using `break`.
   - On 3 failed attempts, locks the card and terminates the session using `break`.
2. **Menu Loop with `do-while` and `switch`:**
   - Presents a 6-option menu (`1. Check Balance`, `2. Deposit`, `3. Withdraw`, `4. Mini-Statement`, `5. Change PIN`, `6. Exit`).
3. **Graceful Error Recovery (`continue` on bad input):**
   - Validates user input with `Scanner.hasNextInt()` and `Scanner.hasNextDouble()`.
   - Strings, letters, and negative amounts never crash the application; an informative error is displayed and the menu loop restarts via `continue`.
4. **Enhanced-`for` Mini-Statement:**
   - Maintains an array of recent transactions (`String[] transactionHistory`).
   - Uses an enhanced `for` loop (`for (String tx : transactionHistory)`) to display recent account activity.
5. **Withdrawal Limits & Validation:**
   - Validates that withdrawal amount is positive, does not exceed current balance, and does not breach the daily ATM limit (`₹25,000.00`).

### File Locations
- Standalone / Direct Run: `Day-03/AtmSimulator.java`
- Maven Packaged Class: `Day-03/src/main/java/com/atm/AtmSimulator.java`
- Project POM: `Day-03/pom.xml`

### Compilation & Execution
```bash
# Direct execution:
javac AtmSimulator.java
java AtmSimulator

# Or via package:
javac src/main/java/com/atm/AtmSimulator.java
java -cp src/main/java com.atm.AtmSimulator
```

---

## Maven Configuration (`pom.xml`) and Profiles

The project includes `pom.xml` configuring Java 17 and environment profiles:
- **`dev` Profile (Active by default):**
  - `app.environment`: `dev`
  - `atm.daily.limit`: `50000.00`
  - `atm.fee.percentage`: `0.00`
  - `debug.mode`: `true`
- **`prod` Profile:**
  - `app.environment`: `prod`
  - `atm.daily.limit`: `20000.00`
  - `atm.fee.percentage`: `0.02`
  - `debug.mode`: `false`

```xml
<profiles>
    <profile>
        <id>dev</id>
        <activation><activeByDefault>true</activeByDefault></activation>
        <properties>
            <app.environment>dev</app.environment>
            <atm.daily.limit>50000.00</atm.daily.limit>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <app.environment>prod</app.environment>
            <atm.daily.limit>20000.00</atm.daily.limit>
        </properties>
    </profile>
</profiles>
```

---

## Project Task: Laundry Service Management Console Menu

Created `Day-03/src/main/java/com/laundry/LaundryMenuApp.java` implementing the 8 Functional Requirements (FR1 - FR8) with input-validation guardrails.

### Functional Menu Options:
1. `[FR1] Manage Services & Item Prices (Admin)`
2. `[FR2] Schedule Laundry Pickup (Customer)`
3. `[FR3] Record Items & Generate Tags (Staff)`
4. `[FR4] Calculate Bill & Surcharges (System)`
5. `[FR5] Update Order Processing Stage (Staff)`
6. `[FR6] Complete Pickup & Delivery (Delivery Agent)`
7. `[FR7] Track Order Status (Customer)`
8. `[FR8] View Daily Production Report (Admin)`
9. `Exit Application`

### Compilation & Execution
```bash
javac src/main/java/com/laundry/LaundryMenuApp.java
java -cp src/main/java com.laundry.LaundryMenuApp
```

### Acceptance Criteria Checklist
- [x] Program runs from terminal with clean execution.
- [x] Menu never crashes on bad or malformed input.
- [x] 3 PIN attempts implemented with `break`.
- [x] `continue` used on invalid entry.
- [x] Mini-statement iterates using enhanced `for` loop.
- [x] Maven POM structure and profiles created.
