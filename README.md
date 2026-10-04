# Employee Management Console Application

A small Java console application for managing employee records in memory. It demonstrates object-oriented design, encapsulation, interfaces, collection-based storage, checked exceptions, and Stream API filtering.

## Features

- Add employee records and display all employees.
- Search for an employee by ID or employees by department.
- Find active employees whose salary is greater than a user-provided threshold.
- Handle missing employee IDs with a custom checked exception.
- Retry invalid integer and decimal input rather than terminating the menu.
- Protect repository data with defensive employee copies and unmodifiable collection results.
- Load four sample employees at startup.

Employee records are stored in memory and are reset whenever the application exits. Employee IDs are map keys; adding a record with an existing ID replaces the previous record.

## Technologies

- Java 11 or later
- Java standard library: `Scanner`, `HashMap`, `List`, and Stream API
- No third-party dependencies or build tool required

## Project Structure

```text
EmployeeManagement/
|-- README.md
|-- src/
|   `-- main/
|       `-- java/
|           |-- Employee.java
|           |-- EmployeeNotFoundException.java
|           |-- EmployeeRepository.java
|           |-- EmployeeRepositoryImpl.java
|           |-- InMemoryEmployeeRepository.java
|           |-- EmployeeService.java
|           |-- EmployeeServiceImpl.java
|           `-- Main.java
`-- out/                         # Generated class files after compilation
```

The source files use the default package. `InMemoryEmployeeRepository` is the repository implementation used by `Main`; it extends `EmployeeRepositoryImpl`, which contains the `HashMap` storage and repository operations.

## Prerequisites

Install a JDK version 11 or later and ensure `javac` and `java` are available on your command-line `PATH`. Java 11 is required because the repository uses `List.copyOf`.

Check the installed versions with:

```text
javac -version
java -version
```

## Compile and Run

Run the following from the project root.

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out src\main\java\*.java
java -cp out Main
```

### macOS, Linux, or Bash

```bash
mkdir -p out
javac -d out src/main/java/*.java
java -cp out Main
```

The menu repeats until option `6` is selected. Numeric prompts reject malformed values and prompt again. The salary filter accepts finite decimal values.

## Startup Sample Data

These four employees are loaded each time the application starts:

| ID | Name | Department | Salary | Active |
|---:|---|---|---:|:---:|
| 1001 | Pradyuman Pratap Singh | Engineering | 92000.00 | Yes |
| 1002 | Manish Mishra | Human Resources | 68000.00 | Yes |
| 1003 | Rahul Kumar Sinha | Finance | 75000.00 | No |
| 1004 | Sahil Babbar | Engineering | 88000.00 | Yes |

## Menu Operations

1. **Add Employee**: Enter an employee ID, name, department, salary, and active status (`true` or `false`).
2. **Display All Employees**: Print every employee currently stored, or a message if there are none.
3. **Search Employee By ID**: Print the matching employee or display the not-found exception message.
4. **Display Employees By Department**: Print employees whose department exactly matches the entered text, or a no-results message.
5. **Display Active Employees With Salary Greater Than**: Print employees that are active and have salary strictly greater than the entered threshold, or a no-results message.
6. **Exit**: End the application.

## Exception Handling

`EmployeeNotFoundException` extends `Exception`, making it a checked exception. Repository ID lookup throws it when the requested ID is absent; `EmployeeService` declares it, and the console catches it during ID search to show a readable message.

The console also catches invalid numeric text and prompts again. It accepts only `true` or `false` for the active-status prompt. Other input validation, such as rejecting blank names, negative salaries, or duplicate IDs, is not implemented; duplicate IDs replace the existing record.

## Stream API

`EmployeeServiceImpl.findActiveEmployeesWithSalaryGreaterThan` streams the repository's employees and applies two filters:

```java
.filter(Employee::isActive)
.filter(employee -> employee.getSalary() > minimumSalary)
```

The returned results therefore include only active employees whose salary exceeds (not equals) the threshold.