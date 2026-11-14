package com.yunhuang.payroll.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payrollrecords")
public class PayrollRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "PayrollPeriodID", nullable = false)
    private PayrollPeriod payrollPeriod;

    private LocalDate paymentDate;

    @Column(precision = 8, scale = 2)
    private BigDecimal regularHours;

    @Column(precision = 8, scale = 2)
    private BigDecimal overtimeHours;

    @Column(precision = 12, scale = 2)
    private BigDecimal regularPay;

    @Column(precision = 12, scale = 2)
    private BigDecimal overtimePay;

    @Column(precision = 12, scale = 2)
    private BigDecimal grossPay;

    @Column(precision = 12, scale = 2)
    private BigDecimal taxAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal otherDeductions;

    @Column(precision = 12, scale = 2)
    private BigDecimal netPay;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PayrollPeriod getPayrollPeriod() {
        return payrollPeriod;
    }

    public void setPayrollPeriod(PayrollPeriod payrollPeriod) {
        this.payrollPeriod = payrollPeriod;
    }

    public BigDecimal getRegularHours() {
        return regularHours;
    }

    public void setRegularHours(BigDecimal regularHours) {
        this.regularHours = regularHours;
    }

    public BigDecimal getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public BigDecimal getRegularPay() {
        return regularPay;
    }

    public void setRegularPay(BigDecimal regularPay) {
        this.regularPay = regularPay;
    }

    public BigDecimal getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(BigDecimal overtimePay) {
        this.overtimePay = overtimePay;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getOtherDeductions() {
        return otherDeductions;
    }

    public void setOtherDeductions(BigDecimal otherDeductions) {
        this.otherDeductions = otherDeductions;
    }

    public BigDecimal getNetPay() {
        return netPay;
    }

    public void setNetPay(BigDecimal netPay) {
        this.netPay = netPay;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}