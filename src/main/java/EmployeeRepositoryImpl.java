import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<Integer, Employee> employees = new HashMap<>();

    @Override
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee must not be null");
        employees.put(employee.getEmployeeId(), copyOf(employee));
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> result = new ArrayList<>(employees.size());
        for (Employee employee : employees.values()) {
            result.add(copyOf(employee));
        }
        return List.copyOf(result);
    }

    @Override
    public Employee findEmployeeById(int employeeId) throws EmployeeNotFoundException {
        Employee employee = employees.get(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
        }
        return copyOf(employee);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (Objects.equals(employee.getDepartment(), department)) {
                result.add(copyOf(employee));
            }
        }
        return List.copyOf(result);
    }

    private Employee copyOf(Employee employee) {
        return new Employee(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.isActive());
    }
}