package com.yunhuang.payroll.controller;

import com.yunhuang.payroll.model.PayRate;
import com.yunhuang.payroll.model.PayRateDto;
import com.yunhuang.payroll.service.EmployeeService;
import com.yunhuang.payroll.service.PayRateService;
import com.yunhuang.payroll.service.DepartmentService;
import jakarta.validation.Valid;
import org.hibernate.metamodel.mapping.Bindable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("payrate")
public class PayRateController {

    private final PayRateService payRateService;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public PayRateController(PayRateService payRateService, EmployeeService employeeService,
                             DepartmentService departmentService) {
        this.payRateService = payRateService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/new")
    public String createPayRate(Model model) {
        PayRate payRate = new PayRate();
        payRate.setEffectiveDate(LocalDate.now());
        model.addAttribute("payrate", payRate);
        return "payrate-create";
    }

    @PostMapping("/save")
    public String savePayRate(
            @Valid @ModelAttribute("payrate") PayRate payRate,
            BindingResult result) {
        if (result.hasErrors()) {

            return "payrate-create";
        }
        PayRate obj = payRateService.savePayRate(payRate);

        return "redirect:/payrate/list";
    }


    @GetMapping("/edit/{id}")
    public String displayPayRateEdit(
            @PathVariable("id") Integer payRateId,
            Model model)
    {
        PayRate payRate = payRateService.getPayRate(payRateId);
        model.addAttribute("payrate", payRate);
        String fullName = payRate.getEmployee().getFirstName() + " " + payRate.getEmployee().getLastName();
        model.addAttribute("fullname", fullName);

        //System.out.println(payRate.getEffectiveDate());
        return "payrate-update";
    }

    @PostMapping("/update")
    public String updatePayrate(
            @RequestParam("id") Integer payRateId,
            @Valid @ModelAttribute("payrate") PayRate payRate,
            BindingResult result) {

        if (result.hasErrors()) {

            return "payrate-update";
        }

        PayRate obj = payRateService.updatePayRate(payRateId, payRate);

        return "redirect:/payrate/list";
    }

    @GetMapping("/list")
    public String displayPayrateList(
            @RequestParam(required = false) Integer departmentid,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            Model model
    )
    {
        List<PayRateDto> payRateDtoList;
        if (firstName != null && lastName != null)
            payRateDtoList = payRateService.getPayrateByEmployee(firstName, lastName, departmentid);
        else
            payRateDtoList = payRateService.getRecentDatePayRate(departmentid, firstName, lastName);
        model.addAttribute("department", departmentService.getDepartments());
        model.addAttribute("payratelist", payRateDtoList);
        model.addAttribute("department", departmentService.getDepartments());
        return "payrate-list";
    }

}
