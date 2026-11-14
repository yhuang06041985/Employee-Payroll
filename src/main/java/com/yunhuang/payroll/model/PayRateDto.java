package com.yunhuang.payroll.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class PayRateDto {
    private Integer id;
    private String fullName;
    private String departmentCode;
    private String payType;
    private BigDecimal baseRate;
    private Date effectiveDate;
    private Date endDate;

    public PayRateDto(Integer id, String fullName, String departmentCode, String payType,
                   BigDecimal baseRate, Date effectiveDate, Date endDate)
    {
       this.id = id;
        this.fullName = fullName;
        this.departmentCode = departmentCode;
        this.payType = payType;
        this.baseRate = baseRate;
        this.effectiveDate = effectiveDate;
        this.endDate = endDate;
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

    public String getPayType()
    {
        return payType;
    }

    public BigDecimal getBaseRate()
    {
        return baseRate;
    }

    public Date getEffectiveDate()
    {
        return effectiveDate;
    }

    public  Date getEndDate()
    {
        return endDate;
    }
}
