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


    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee.getCompensation();
    }

    @Override
    public Compensation update(Employee employee) {
        LOG.debug("Updating employee with new compensation [{}]", employee);

        Employee found = employeeRepository.findByEmployeeId(employee.getEmployeeId());
        found.setCompensation(employee.getCompensation());
        employeeRepository.save(found);

        return found.getCompensation();
    }
}
