package model;

public class Employee {
    private String empId;
    private String empName;
    private String department;

    public Employee(String empId, String empName, String department) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
    }

    public Employee(Employee other) {
        this.empId = other.empId;
        this.empName = other.empName;
        this.department = other.department;
    }

    public String getEmpId() { return empId; }
    public String getEmpName() { return empName; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return empName + " (" + empId + ") – " + department;
    }
}
