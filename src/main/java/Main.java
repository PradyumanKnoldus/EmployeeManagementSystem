import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository repository = new InMemoryEmployeeRepository();
        EmployeeService service = new EmployeeServiceImpl(repository);
        addSampleEmployees(service);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = readInt(scanner, "Choose an option: ");

                switch (choice) {
                    case 1:
                        addEmployee(scanner, service);
                        break;
                    case 2:
                        displayAllEmployees(service);
                        break;
                    case 3:
                        searchEmployeeById(scanner, service);
                        break;
                    case 4:
                        displayEmployeesByDepartment(scanner, service);
                        break;
                    case 5:
                        displayActiveEmployeesAboveSalary(scanner, service);
                        break;
                    case 6:
                        running = false;
                        System.out.println("Exiting Employee Management System.");
                        break;
                    default:
                        System.out.println("Invalid option. Enter a number from 1 to 6.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nEmployee Management System");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Search Employee By ID");
        System.out.println("4. Display Employees By Department");
        System.out.println("5. Display Active Employees With Salary Greater Than");
        System.out.println("6. Exit");
    }

    private static void addEmployee(Scanner scanner, EmployeeService service) {
        int employeeId = readInt(scanner, "Employee ID: ");
        String name = readText(scanner, "Name: ");
        String department = readText(scanner, "Department: ");
        double salary = readDouble(scanner, "Salary: ");
        boolean active = readBoolean(scanner, "Active (true/false): ");

        service.addEmployee(new Employee(employeeId, name, department, salary, active));
        System.out.println("Employee added successfully.");
    }

    private static void displayAllEmployees(EmployeeService service) {
        List<Employee> employees = service.displayAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        employees.forEach(System.out::println);
    }

    private static void searchEmployeeById(Scanner scanner, EmployeeService service) {
        int employeeId = readInt(scanner, "Employee ID to search: ");
        try {
            System.out.println(service.getEmployeeById(employeeId));
        } catch (EmployeeNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void displayEmployeesByDepartment(Scanner scanner, EmployeeService service) {
        String department = readText(scanner, "Department to search: ");
        List<Employee> employees = service.findEmployeesByDepartment(department);
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
            return;
        }
        employees.forEach(System.out::println);
    }

    private static void displayActiveEmployeesAboveSalary(Scanner scanner, EmployeeService service) {
        double minimumSalary = readDouble(scanner, "Minimum salary (exclusive): ");
        List<Employee> employees = service.findActiveEmployeesWithSalaryGreaterThan(minimumSalary);
        if (employees.isEmpty()) {
            System.out.println("No active employees found with salary greater than " + minimumSalary + ".");
            return;
        }
        employees.forEach(System.out::println);
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number. Please enter a finite numeric value.");
                continue;
            }
            System.out.println("Invalid number. Please enter a finite numeric value.");
        }
    }

    private static boolean readBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (value.equalsIgnoreCase("true")) {
                return true;
            }
            if (value.equalsIgnoreCase("false")) {
                return false;
            }
            System.out.println("Please enter true or false.");
        }
    }

    private static String readText(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static void addSampleEmployees(EmployeeService service) {
        service.addEmployee(new Employee(1001, "Pradyuman Pratap Singh", "Engineering", 92000.0, true));
        service.addEmployee(new Employee(1002, "Manish Mishra", "Human Resources", 68000.0, true));
        service.addEmployee(new Employee(1003, "Rahul Kumar Sinha", "Finance", 75000.0, false));
        service.addEmployee(new Employee(1004, "Sahil Babbar", "Engineering", 88000.0, true));
    }
}