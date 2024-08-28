package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /*
    Find employee, if employee is null throw an error
    otherwise get the compensation object of the found employee
    modeled after EmployeeServiceImpl.read for consistency
     */
    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee.getCompensation();
    }

    /*
    Find employee using a passed ID and compensation package
    If employee exists replace it with the passed compensation package
    then return the compensation package
    To see further thoughts on this endpoint and an alternative implementation I would want to do if I could talk to
    another dev, see Compensation.java
     */
    @Override
    public Compensation update(Employee employee) {
        LOG.debug("Updating employee with new compensation [{}]", employee);

        Employee found = employeeRepository.findByEmployeeId(employee.getEmployeeId());

        if (found == null) {
            throw new RuntimeException("Invalid employee object to update: " + employee.getEmployeeId());
        }

        found.setCompensation(employee.getCompensation());
        employeeRepository.save(found);

        return found.getCompensation();
    }
}
