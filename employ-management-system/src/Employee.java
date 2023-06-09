import java.util.HashSet;
import java.util.Set;

public class Employee {
    private Integer id;
    private String name;
    private Set<Department> departments;
    private boolean fullTime;
    private double salary;

    public Employee(Integer id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.departments = new HashSet<>();
        this.fullTime = isFulltimer(this.salary);
    }

    private static boolean  isFulltimer(double salary){
        return salary >= 20000;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name=" + name +
                ", departments=" + departments.toString() +
                ", fullTime=" + fullTime +
                ", salary=" + salary +
                '}';
    }

    public void removeDepartment(Department department) {
        this.departments.remove(department);
    }
}
