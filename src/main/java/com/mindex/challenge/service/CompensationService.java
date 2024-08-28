package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

/*
interface modelled after the EmployeeService for consistency
 */
public interface CompensationService {
    Compensation update(Employee employee);
    Compensation read(String id);
}
