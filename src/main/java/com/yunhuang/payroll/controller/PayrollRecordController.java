package com.yunhuang.payroll.controller;

import com.yunhuang.payroll.model.Employee;
import com.yunhuang.payroll.model.EmployeePayrateDto;
import com.yunhuang.payroll.model.PayrollRecord;
import com.yunhuang.payroll.service.*;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payrollrecord")
public class PayrollRecordController {

    private final PayrollRecordService payrollRecordService;
    private final PayrollPeriodService payrollPeriodService;
    private final DepartmentService departmentService;

    public PayrollRecordController(
            PayrollPeriodService payrollPeriodService,
            PayrollRecordService payrollRecordService,
            PayRateService payRateService,
            EmployeeService employeeService,
            DepartmentService departmentService) {

        this.payrollRecordService = payrollRecordService;
        this.payrollPeriodService = payrollPeriodService;
        this.departmentService = departmentService;
    }

    @GetMapping("/new")
    public String create(Model model) {

        model.addAttribute("payrollrecord", new PayrollRecord());
        model.addAttribute(
                "currentPayPeriod",
                payrollPeriodService.getCurrentPeiod());

        return "payrollrecord-create";
    }

    @GetMapping("/select-employee")
    public String searchEmployee(Model model) {

        model.addAttribute("employee", new Employee());
        model.addAttribute(
                "departments",
                departmentService.getDepartments());

        return "search-employee-payrate";
    }

    @GetMapping("/search-employee-payrate")
    public String getEmployeeName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer departmentId,
            Model model) {

        if (firstName != null && firstName.isBlank()) {
            firstName = null;
        }

        if (lastName != null && lastName.isBlank()) {
            lastName = null;
        }

        List<EmployeePayrateDto> employeePayrateDtos =
                payrollRecordService.getEmployeePayrate(
                        firstName, lastName, departmentId);

        model.addAttribute("employeePayrateDtos", employeePayrateDtos);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute(
                "departments",
                departmentService.getDepartments());

        return "search-employee-payrate";
    }

    @PostMapping("/save")
    public String savePayrollRecord(
            @Valid @ModelAttribute("payrollrecord") PayrollRecord payrollRecord,
            BindingResult result,
            @RequestParam("employeeId") Integer employeeId,
            @RequestParam("payRateId") Integer payRateId,
            @RequestParam("payPeriodId") Integer payPeriodId,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "currentPayPeriod",
                    payrollPeriodService.getPayrollPeriod(payPeriodId));

            return "payrollrecord-create";
        }

        payrollRecordService.savePayrollRecord(
                employeeId,
                payPeriodId,
                payRateId,
                payrollRecord);

        return "redirect:/";
    }
}