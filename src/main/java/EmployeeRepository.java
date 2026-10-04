import java.util.List;

public interface EmployeeRepository {
    void addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee findEmployeeById(int employeeId) throws EmployeeNotFoundException;

    List<Employee> getEmployeesByDepartment(String department);
}
