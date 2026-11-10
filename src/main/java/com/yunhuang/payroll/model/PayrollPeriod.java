package com.yunhuang.payroll.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;
import com.yunhuang.payroll.validation.ValidPayrollPeriodDates;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "PayrollPeriods")
public class PayrollPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @NotBlank(message = "Period Name is required")
    private String periodName;

    @NotNull(message = "Start Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Pay Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate payDate;

    public Integer getId()
    {
        return  Id;
    }

    public void setId(Integer Id)
    {
        this.Id = Id;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setPeriodName(String periodName)
    {
        this.periodName = periodName;
    }

    public String getPeriodName()
    {

        return periodName;
    }

    public  void setPayDate(LocalDate payDate)
    {
        this.payDate = payDate;
    }

    public LocalDate getPayDate()
    {
        return payDate;
    }

}
