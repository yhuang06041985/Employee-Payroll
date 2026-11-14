package com.yunhuang.payroll.service;

import com.yunhuang.payroll.model.Employee;
import com.yunhuang.payroll.model.PayRate;
import com.yunhuang.payroll.model.PayRateDto;
import com.yunhuang.payroll.repository.PayRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayRateService {
    private final PayRateRepository payRateRepository;

    public PayRateService(PayRateRepository payRateRepository)
    {
        this.payRateRepository = payRateRepository;
    }

    public PayRate savePayRate(PayRate payRate)
    {
        return payRateRepository.save(payRate);
    }

    public PayRate updatePayRate(Integer id, PayRate payRate)
    {
        PayRate obj = payRateRepository.findById(id).orElse(null);
        if (obj != null)
        {
            obj.setEffectiveDate(payRate.getEffectiveDate());
            //Employee employee = payRate.getEmployee();
            //obj.setEmployee(employee);
            obj.setEndDate(payRate.getEndDate());
            obj.setRateType(payRate.getRateType());
            obj.setBaseRate(payRate.getBaseRate());
            payRateRepository.save(obj);
            return obj;
        }
        return payRate;
    }

    public PayRate getPayRate(Integer id)
    {
        return payRateRepository.findById(id).orElse(null);
    }

    //get payrate has full name, departcode, payrate and very recently effective date
    public List<PayRateDto> getRecentDatePayRate(Integer deptId, String firstName, String lastName)
    {
        return payRateRepository.findLatestPayRatesByDepartment(deptId, firstName, lastName);
    }

    public List<PayRateDto> getPayrateByEmployee(String firstName, String lastName, Integer departmentId)
    {
        return payRateRepository.findPayRateByEmployee(firstName, lastName, departmentId);
    }
}
