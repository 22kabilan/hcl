\# Day 03 - Control Flow + Maven



\## Topics Covered



\### Java Control Flow

\- `if-else` statements

\- `switch` statements

\- `while` and `do-while` loops

\- `for` and enhanced `for` loops

\- `break` and `continue`

\- Input validation and conditional logic



\### Maven Fundamentals

\- Maven project structure

\- Project Object Model (`pom.xml`)

\- Maven coordinates: `groupId`, `artifactId`, and `version`

\- Maven build lifecycle

\- Build commands: `clean`, `compile`, `test`, `package`, and `install`

\- Maven profiles for development and production environments



\## Daily Hands-on Task: ATM Simulator



Developed a console-based ATM Simulator using Java.



\### Features

\- PIN authentication with a maximum of three attempts

\- Menu-driven interface using `do-while` and `switch`

\- Balance inquiry

\- Deposit and withdrawal operations

\- Input validation for menu choices and transaction amounts

\- Use of `break` and `continue` for control flow

\- Mini-statement using an array and enhanced `for` loop



\### Technologies Used

\- Java

\- Apache Maven



\## Maven Build and Execution



Compile and package the project:



```bash

mvn clean package

```



Activate the development profile:



```bash

mvn help:evaluate -Dexpression=app.environment -q -DforceStdout -Pdev

```



Activate the production profile:



```bash

mvn help:evaluate -Dexpression=app.environment -q -DforceStdout -Pprod

```



Expected profile values:



\- Development: `dev`

\- Production: `prod`



\## Project Task: Laundry Service Management



Created the initial Maven project structure for the Laundry Service Management system.



\### Functional Requirements Represented in the Console Menu

1\. Manage services and item prices (Admin)

2\. Schedule pickup (Customer)

3\. Record items and generate tags (Staff)

4\. Calculate bills, including express surcharge

5\. Update processing stages (Staff)

6\. Complete pickup and delivery (Delivery Agent)

7\. Track order status (Customer)

8\. View daily production reports (Admin)



\### Maven Modules

\- `auth-service`

\- `laundry-service`

\- `logistics-service`





