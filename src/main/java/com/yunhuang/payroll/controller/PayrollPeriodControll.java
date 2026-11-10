package com.yunhuang.payroll.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.yunhuang.payroll.model.PayrollPeriod;
import com.yunhuang.payroll.service.PayrollPeriodService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/payrollperiod")
public class PayrollPeriodControll {

    private final PayrollPeriodService payrollPeriodService;
    public  PayrollPeriodControll(PayrollPeriodService payrollPeriodService)
    {
        this.payrollPeriodService = payrollPeriodService;
    }
    @GetMapping("/new")
    public String Create(Model model)
    {
        model.addAttribute("payrollperiod", new PayrollPeriod());
        return "payroll-period-create";
    }

    @PostMapping("/save")
    public String savePayrollPeriod(@Valid @ModelAttribute("payrollperiod")
                 PayrollPeriod payrollPeriod,
                  BindingResult result) {

        if (result.hasErrors())
            return "payrollperiod";

        PayrollPeriod obj = payrollPeriodService.savePayrollPeriod(payrollPeriod);

        //model.addAttribute("departments", departmentService.getDepartments());
        //return "redirect:/payrollperiod/list";
        return "redirect:/";
    }

    @GetMapping("/list")
    public String getPayrollPeriodList(Model model)
    {
        model.addAttribute("payrollperiodlist", payrollPeriodService.getPayrollPeriods());
        return "payroll-period-list";
    }

    @GetMapping("/edit/{id}")
    public String getPayrollPeriod(
            @PathVariable("id") Integer id,
            Model model)
      {
        PayrollPeriod payrollPeriod = payrollPeriodService.getPayrollPeriod(id);
        model.addAttribute("payrollperiod", payrollPeriod);

         // System.out.println("Start Date: " + payrollPeriod.getStartDate());

        return "payroll-period-update";
      }

     @PostMapping("update/{id}")
    public String updatePayrollPeriod(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute("payrollperiod") PayrollPeriod payrollPeriod,
              BindingResult result) {

         if (result.hasErrors())
             return "payroll-period-update";
         PayrollPeriod obj = payrollPeriodService.updatePayrollPeriod(id, payrollPeriod);
         return "redirect:/payrollperiod/list";
     }
}
