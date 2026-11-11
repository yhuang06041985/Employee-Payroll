package com.yunhuang.payroll.model;

import jakarta.persistence.*;
import com.yunhuang.payroll.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="payrates")
public class PayRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;
    private String rateType;
    private BigDecimal baseRate;
    private LocalDate effectiveDate;
    private LocalDate endDate;

    public void setId(Integer Id)
    {
        this.Id = Id;
    }

    public Integer getId()
    {
        return Id;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setRateType(String rateType)
    {
        this.rateType = rateType;
    }

    public String getRateType()
    {
        return  rateType;
    }

    public void setEffectiveDate(LocalDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setBaseRate(BigDecimal baseRate)
    {
        this.baseRate = baseRate;
    }

    public BigDecimal getBaseRate()
    {
        return baseRate;
    }

}