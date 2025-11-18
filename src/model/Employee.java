package model;

//Represents an employee in the organization
public class Employee {

    private String empId;
    private String empName;
    private String department;

//Default constructor -constructor chaining this()
    public Employee() {
        this("NA", "Unknown", "General");
    }

    public Employee(String empId, String empName, String department) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
    }

//Copy constructor for defensive copying
    public Employee(Employee other) {
        this(other.empId, other.empName, other.department);
    }

    public String getEmpId() { return empId; }
    public String getEmpName() { return empName; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return empName + " (" + empId + ") – " + department;
    }
}
