import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = Objects.requireNonNull(employeeRepository, "employeeRepository must not be null");
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
    }

    @Override
    public List<Employee> displayAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findEmployeeById(employeeId);
    }

    @Override
    public List<Employee> findEmployeesByDepartment(String department) {
        return employeeRepository.getEmployeesByDepartment(department);
    }

    @Override
    public List<Employee> findActiveEmployeesWithSalaryGreaterThan(double minimumSalary) {
        return employeeRepository.getAllEmployees().stream()
                .filter(Employee::isActive)
                .filter(employee -> employee.getSalary() > minimumSalary)
                .collect(Collectors.toList());
    }
}