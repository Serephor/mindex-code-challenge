package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {

    // Required fields according to task 1
    private Employee employee;
    private int numberOfReports;

    // Generic constructor
    public ReportingStructure(){

    }

    // Getters and Setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    // As this is meant to be done and use on an ad hoc basis - I omitted a builder here, see notes In Employee on
    // why I would create a builder if this was stored data.
}
