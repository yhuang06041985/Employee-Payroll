package com.yunhuang.payroll.model;

import java.math.BigDecimal;
import java.util.List;

public class EmployeePayrateDto {
    private Integer employeeId;
    private String fullName;
    private Integer payrateId;
    private String rateType;
    private BigDecimal baseRate;

    public  EmployeePayrateDto(Integer employeeId,
                                                       String fullName,
                                                       Integer payrateId,
                                                       String rateType,
                                                       BigDecimal baseRate)
    {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.payrateId = payrateId;
        this.rateType = rateType;
        this.baseRate = baseRate;
    }

    public Integer getEmployeeId()
    {
        return employeeId;
    }

    public String getFullName()
    {
        return fullName;
    }

    public Integer getPayrateId()
    {
        return payrateId;
    }

    public String getRateType()
    {
        return rateType;
    }

    public BigDecimal getBaseRate()
    {
        return  baseRate;
    }
}
