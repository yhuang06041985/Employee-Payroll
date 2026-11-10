package com.yunhuang.payroll.controller;
import com.yunhuang.payroll.model.Department;
import com.yunhuang.payroll.model.Employee;
import com.yunhuang.payroll.model.PayrollPeriod;
import com.yunhuang.payroll.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.yunhuang.payroll.service.DepartmentService;

// pagination
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("departments", departmentService.getDepartments());
        return "employee-create";
    }

    @PostMapping("/save")
    public String createEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            @RequestParam("departmentId") Integer departmentId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getDepartments());
            return "employee-create";
        }

        Department department = departmentService.getDepartment(departmentId);

        employee.setDepartment(department);
        Employee obj = employeeService.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/list")
    public String listEmployees(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(
                page,
                2,  // determine pagesize here
                Sort.by("firstName").ascending()
        );

        Page<Employee> employeePage =
                employeeService.getAllEmployees(pageable);

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("employees", employeePage.getContent());

        return "employee-list";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Integer employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("departmentId", employeeService.getDepartmentId(employee));

        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Hire Date: " + employee.getHireDate());

        return "employee-update";
    }

    @PostMapping("/update")
    public String updateEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            @RequestParam("departmentId") Integer departmentId,
            @RequestParam("employeeId") Integer employeeId,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getDepartments());
            model.addAttribute("departmentId", departmentId);
            return "department-update";
        }

        Department department = departmentService.getDepartment(departmentId);
        employee.setDepartment(department);
        Employee obj = employeeService.updateEmployee(employeeId, employee);
        return "redirect:/employee/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id)
    {
        employeeService.removeEmployee(id);
        return "redirect:/employee/list";
    }


}



