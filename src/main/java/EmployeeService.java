import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);

    List<Employee> displayAllEmployees();

    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;

    List<Employee> findEmployeesByDepartment(String department);

    List<Employee> findActiveEmployeesWithSalaryGreaterThan(double minimumSalary);
}
