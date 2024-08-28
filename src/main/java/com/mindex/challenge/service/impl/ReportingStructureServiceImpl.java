package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /*
    Check to find employee and if it is valid - then continue
    Create a reporting structure with the employee and then recursively find the number of reports
     */
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Reading reporting structure of employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure reportingStructure = new ReportingStructure();

        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(recurseTotalReports(employee));

        return reportingStructure;
    }

    /*
    Another reason I would like to consider precomputing the reporting structure would to possibly prevent a stack
    overflow, as this is a recursive method it can be stack intensive if the tree gets too deep. More food for thought
    that I would like to talk over with the product owner and consumer.
     */
    private int recurseTotalReports(Employee employee) {
        List<Employee> directReports = employee.getDirectReports();

        // Check to see if the current employee has anyone that reports to them
        // If they don't, they have no direct reporters and thus return 0
        if (directReports == null || directReports.isEmpty()) {
            return 0;
        }

        // Otherwise the amount of direct reporters is the size of the list
        int totalReports = directReports.size();

        // Then do the same process for every employee in that list
        for (Employee reporter : employee.getDirectReports()) {
            totalReports += recurseTotalReports(employeeRepository.findByEmployeeId(reporter.getEmployeeId()));
        }

        return  totalReports;
    }
}
