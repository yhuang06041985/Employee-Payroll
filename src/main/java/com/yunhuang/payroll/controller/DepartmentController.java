package com.yunhuang.payroll.controller;

import com.yunhuang.payroll.service.DepartmentService;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yunhuang.payroll.model.Department;
import java.util.List;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/department")

public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/new")
    public String create(Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        return "department";
    }

    @GetMapping("/edit/{id}")
    public String getDepartment(@PathVariable("id") Integer id,
                                Model model) {
        model.addAttribute("department", departmentService.getDepartment(id));
        return "updateDepartment";
    }

    @GetMapping("/list")
    public String getAllDepartment(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        return "DepartmentList";
    }


    @PostMapping("/save")
    public String saveDepartment(
            @Valid @ModelAttribute("department") Department department,
            BindingResult result) {

        if (result.hasErrors())
            return "Department";

        Department obj = departmentService.saveDepartment(department);

        //model.addAttribute("departments", departmentService.getDepartments());
        return "redirect:/department/list";
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute("department") Department department,
            BindingResult result) {
        if (result.hasErrors())
            return "Department";
        department.setDepartmentId(id);
        Department obj = departmentService.updateDepartment(department, id);

        return "redirect:/department/list";
    }
}