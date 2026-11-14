package com.yunhuang.payroll.service;

import com.yunhuang.payroll.model.*;
import com.yunhuang.payroll.repository.PayrollRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PayrollRecordService {

    private final PayrollRecordRepository payrollRecordRepository;
    private final PayrollPeriodService payrollPeriodService;
    private final EmployeeService employeeService;
    private final PayRateService payRateService;

    public PayrollRecordService(
            PayrollRecordRepository payrollRecordRepository,
            PayrollPeriodService payrollPeriodService,
            PayRateService payRateService,
            EmployeeService employeeService) {

        this.employeeService = employeeService;
        this.payrollPeriodService = payrollPeriodService;
        this.payRateService = payRateService;
        this.payrollRecordRepository = payrollRecordRepository;
    }

    public PayrollRecord getPayrollRecord(Integer id) {
        return payrollRecordRepository.findById(id).orElse(null);
    }

    public PayrollRecord savePayrollRecord(
            Integer employeeId,
            Integer payPeriodId,
            Integer payRateId,
            PayrollRecord payrollRecord) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        PayrollPeriod payrollPeriod =
                payrollPeriodService.getPayrollPeriod(payPeriodId);
        PayRate payRate = payRateService.getPayRate(payRateId);

        payrollRecord.setPayrollPeriod(payrollPeriod);

        payrollRecord.setStatus("Pending");

        //payrollRecord.setPayRate(payRate);
        payrollRecord.setEmployee(employee);

        payrollRecord.setPaymentDate(payrollPeriod.getPayDate());

        calculatePayroll(payrollRecord, payRate);

        return payrollRecordRepository.save(payrollRecord);
    }

    public PayrollRecord updateayrollRecord(
            Integer payrollRecordId,
            Integer employeeId,
            Integer payPeriodId,
            Integer payRateId,
            PayrollRecord payrollRecord) {

        PayrollRecord existingRecord =
                payrollRecordRepository.findById(payrollRecordId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Payroll record not found: " + payrollRecordId));

        // Only update the editable payroll fields.
        // Keep the original employee, pay rate, pay period and payment date.
        existingRecord.setRegularHours(payrollRecord.getRegularHours());
        existingRecord.setOvertimeHours(payrollRecord.getOvertimeHours());
        existingRecord.setTaxAmount(payrollRecord.getTaxAmount());
        existingRecord.setOtherDeductions(payrollRecord.getOtherDeductions());

       // calculatePayroll(existingRecord, existingRecord.getPayRate());

        return payrollRecordRepository.save(existingRecord);
    }

    private void calculatePayroll(
            PayrollRecord payrollRecord,
            PayRate payRate) {

        if (payRate == null) {
            throw new IllegalArgumentException("Pay rate is required.");
        }

        if (payrollRecord.getTaxAmount() == null) {
            payrollRecord.setTaxAmount(BigDecimal.ZERO);
        }

        if (payrollRecord.getOtherDeductions() == null) {
            payrollRecord.setOtherDeductions(BigDecimal.ZERO);
        }

        if ("Hourly".equalsIgnoreCase(payRate.getRateType())) {
            calculatHourly(payrollRecord, payRate);
        } else {
            calculteSalary(payrollRecord, payRate);
        }
    }

    public PayrollRecord calculteSalary(PayrollRecord payrollRecord, PayRate payRate) {

        payrollRecord.setRegularHours(BigDecimal.ZERO);
        payrollRecord.setOvertimeHours(BigDecimal.ZERO);
        payrollRecord.setOvertimePay(BigDecimal.ZERO);
        payrollRecord.setRegularPay(BigDecimal.ZERO);
        BigDecimal grossPay = payRate.getBaseRate()
                .divide(BigDecimal.valueOf(26), 2, RoundingMode.HALF_UP);

        payrollRecord.setRegularPay(grossPay);

        BigDecimal netPay = grossPay
                .subtract(payrollRecord.getTaxAmount())
                .subtract(payrollRecord.getOtherDeductions());

        //payrollRecord.setRegularHours(null);
        //payrollRecord.setOvertimeHours(null);
        //payrollRecord.setRegularPay(null);
        //payrollRecord.setOvertimePay(null);

        payrollRecord.setGrossPay(grossPay);
        payrollRecord.setNetPay(netPay);

        return payrollRecord;
    }

    public PayrollRecord calculatHourly(PayrollRecord payrollRecord, PayRate payRate) {

        BigDecimal regularHours = payrollRecord.getRegularHours() == null
                ? BigDecimal.ZERO : payrollRecord.getRegularHours();

        BigDecimal overtimeHours = payrollRecord.getOvertimeHours() == null
                ? BigDecimal.ZERO : payrollRecord.getOvertimeHours();

        BigDecimal baseRate = payRate.getBaseRate();

        BigDecimal regularPay = baseRate.multiply(regularHours);

        BigDecimal overtimePay = baseRate
                .multiply(BigDecimal.valueOf(1.5))
                .multiply(overtimeHours);

        BigDecimal grossPay = regularPay.add(overtimePay);

        BigDecimal netPay = grossPay
                .subtract(payrollRecord.getTaxAmount())
                .subtract(payrollRecord.getOtherDeductions());

        payrollRecord.setRegularPay(regularPay);
        payrollRecord.setOvertimePay(overtimePay);
        payrollRecord.setGrossPay(grossPay);
        payrollRecord.setNetPay(netPay);

        return payrollRecord;
    }

    public List<EmployeePayrateDto> getEmployeePayrate(
            String firstName,
            String lastName,
            Integer deptId) {

        return payrollRecordRepository.findEmployeePayrate(
                firstName, lastName, deptId);
    }

    public List<PayrollSummaryDto> getPayrollSummary(String firstName, String lastName, Integer departmentId)
    {
        return payrollRecordRepository.findPayrollSummary(firstName, lastName, departmentId);
    }

    public List<PayrollSummaryDto> getDetailSummaryPayroll(String firstName, String lastName)
    {
        return payrollRecordRepository.findDetailSummaryPayroll(firstName, lastName);
    }
}