package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Compensation {

    // Fields
    private BigDecimal salary;
    private LocalDate effectiveDate;

    // Generic Constructor
    public Compensation() {

    }

    // Getters and Setters
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    //Builder, we can modify this down the line based on other additions to the compensation package
    // - as well as validation
    public static Compensation createCompensation(BigDecimal salary, LocalDate effectiveDate) {
        Compensation retComp = new Compensation();
        retComp.setSalary(salary);
        retComp.setEffectiveDate(effectiveDate);
        return retComp;
    }
}
