import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Employee e1 = new Employee(1,"Kishor",50000);
        Employee e2 = new Employee(2,"Ramesh",10000);
        Employee e3 = new Employee(3,"Suresh",20000);
        Employee e4 = new Employee(4,"Mahesh",30000);
        Employee e5 = new Employee(5,"Rajesh",40000);
        Employee e6 = new Employee(6,"Ramesh",50000);
        Employee e7 = new Employee(7,"Suresh",60000);
        Employee e8 = new Employee(8,"Mahesh",70000);
        Employee e9 = new Employee(9,"Rajesh",80000);

        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.addEmployees(new ArrayList<>(Arrays.asList(e1,e2,e3,e4,e5,e6,e7,e8,e9)));
        ems.printEmployees();
    }
}