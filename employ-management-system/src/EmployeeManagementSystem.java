import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EmployeeManagementSystem {
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    private HashMap<Integer, Employee> employeesData = new HashMap<Integer, Employee>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employeesData.put(employee.getId(), employee);
    }
    public void removeEmployee(Integer id) {
        employees.remove(employeesData.get(id));
        employeesData.remove(id);
    }
    // addEmployees
    public void addEmployees(ArrayList<Employee> _employees) {
        employees.addAll(_employees);
        _employees.forEach(employee -> employeesData.put(employee.getId(), employee));
    }
    public void printEmployees() {
        employees.forEach(System.out::println);
    }
    public void printDepartments(Integer id) {
        employees.stream().filter(employee -> Objects.equals(employee.getId(), id)).forEach(System.out::println);
    }
    public void addDepartment(Integer id, Department department) {
        employees.stream().filter(employee -> Objects.equals(employee.getId(), id)).forEach(employee -> employee.addDepartment(department));
    }
    public void removeDepartment(Integer id, Department department) {
        employees.stream().filter(employee -> Objects.equals(employee.getId(), id)).forEach(employee -> employee.removeDepartment(department));
    }


}


