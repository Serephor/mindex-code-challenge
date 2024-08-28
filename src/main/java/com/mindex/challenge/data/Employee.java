package com.mindex.challenge.data;

import java.util.List;

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;
    private Compensation compensation;

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /*
     Something about the reporting structure that I would like to consider for the future -
     It was mentioned that the calls to get the reporting structure should be computed on the fly in the README
     I would like to ask if it is possible to pre-compute the size and store it within the database.
     Depending on how many calls reporting structure gets (I cannot imagine too many, but it would be good to check)
     it could be an opportunity to save on time and resources.
     */
    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    /*
        Added compensation within the employee object, as each employee would have a form of compensation
        also this way it is easily expandable in the compensation package if there were to be the additions of:
         - healthcare
         - dental
         - insurance
         - etc, etc

         This way everything can remain under a singular "compensation object" rather than having to refactor employee
         later down the line
     */
    public Compensation getCompensation() {
        return compensation;
    }

    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }
}
