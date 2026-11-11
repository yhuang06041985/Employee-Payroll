package com.yunhuang.payroll.controller;

import com.yunhuang.payroll.model.PayRate;
import com.yunhuang.payroll.service.EmployeeService;
import com.yunhuang.payroll.service.PayRateService;
import jakarta.validation.Valid;
import org.hibernate.metamodel.mapping.Bindable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("payrate")
public class PayRateController {

    private final PayRateService payRateService;
    private final EmployeeService employeeService;

    public PayRateController(PayRateService payRateService, EmployeeService employeeService) {
        this.payRateService = payRateService;
        this.employeeService = employeeService;
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

        return "redirect:/";
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
            @PathVariable("id") Integer payRateId,
            @Valid @ModelAttribute("payrate") PayRate payRate,
            BindingResult result) {

        if (result.hasErrors()) {

            return "payrate-update";
        }

        PayRate obj = payRateService.updatePayRate(payRateId, payRate);

        return "redirect:/";
    }

}
