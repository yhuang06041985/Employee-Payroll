package com.yunhuang.payroll.model;

import java.math.BigDecimal;

public class PayrollSummaryDto {
    private Integer id;
    private String fullName;
    private String departmentCode;
    private BigDecimal grossPay;
    private BigDecimal taxAmount;
    private BigDecimal otherDeductions;
    private BigDecimal netPay;

    public PayrollSummaryDto(Integer id, String fullName, String departmentCode, BigDecimal grossPay,
                             BigDecimal taxAmount, BigDecimal otherDeductions, BigDecimal netPay) {
        this.fullName = fullName;
        this.departmentCode = departmentCode;
        this.grossPay = grossPay;
        this.taxAmount = taxAmount;
        this.otherDeductions = otherDeductions;
        this.netPay = netPay;
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getDepartmentCode()
    {
        return departmentCode;
    }

    public BigDecimal getGrossPay()
    {
        return grossPay;
    }

    public BigDecimal getTaxAmount()
    {
        return taxAmount;
    }

    public BigDecimal getOtherDeductions()
    {
        return otherDeductions;
    }

    public BigDecimal getNetPay()
    {
        return netPay;
    }
    // Getters and Setters
}