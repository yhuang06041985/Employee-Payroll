package com.yunhuang.payroll.controller;
import com.yunhuang.payroll.model.Department;
import com.yunhuang.payroll.model.Employee;
import com.yunhuang.payroll.model.EmployeeSearchDTO;
import com.yunhuang.payroll.model.PayrollPeriod;
import com.yunhuang.payroll.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.List;

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

   /* @GetMapping("/list")
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
    } */

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


    @GetMapping("/list")
    public String listEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        // Convert empty strings to null
        if (firstName != null && firstName.isBlank()) {
            firstName = null;
        }

        Pageable pageable = PageRequest.of(
                page,
                2,
                Sort.by(Sort.Direction.ASC, "firstName")
        );

        Page<Employee> employeePage =
                employeeService.searchEmployees(
                        firstName,
                        startDate,
                        endDate,
                        departmentId,
                        pageable
                );

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("employees", employeePage.getContent());

        // Preserve search criteria
        model.addAttribute("firstName", firstName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("departmentId", departmentId);

        model.addAttribute("departments",
                departmentService.getDepartments());

        return "employee-list";
    }

    @GetMapping("/search-popup")
    public String goToEmployeeSearch(Model model)
    {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getDepartments());
        return "employeeSearch";
    }


    @GetMapping("/search-employee-name")
    public String getEmployeeName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer departmentId,
            Model model) {

        // Convert empty strings to null
        if (firstName != null && firstName.isBlank()) {
            firstName = null;
        }
        List<EmployeeSearchDTO> EmployeeSearchDTO = employeeService.getEmployeesName(firstName, lastName, departmentId);
        model.addAttribute("EmployeeSearchDTO", EmployeeSearchDTO);
        // Preserve search criteria
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);

        model.addAttribute("departments",
                departmentService.getDepartments());

        return "employeeSearch";
    }

}



