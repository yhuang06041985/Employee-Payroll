package com.yunhuang.payroll.service;

import com.yunhuang.payroll.model.PayrollPeriod;
import com.yunhuang.payroll.repository.PayrollPeeriodRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollPeriodService {

    private final PayrollPeeriodRepository payrollPeriodRepository;

    public PayrollPeriodService(PayrollPeeriodRepository payrollPeriodRepository)
    {
        this.payrollPeriodRepository = payrollPeriodRepository;
    }

    public List<PayrollPeriod> getPayrollPeriods()
    {
        return payrollPeriodRepository.findAll();
    }

    public PayrollPeriod getPayrollPeriod(Integer id)
    {
        return payrollPeriodRepository.findById(id).orElse(null);

        //return payrollPeriodRepository.findBy(id).orElse(null);
    }

    public PayrollPeriod savePayrollPeriod(PayrollPeriod payrollPeriod)
    {
        return payrollPeriodRepository.save(payrollPeriod);
    }

    public PayrollPeriod updatePayrollPeriod(Integer id, PayrollPeriod payrollPeriod)
    {
        PayrollPeriod updatePayrollPeriod = payrollPeriodRepository.findById(id).orElse(null);
        updatePayrollPeriod.setStartDate(payrollPeriod.getStartDate());
        updatePayrollPeriod.setEndDate(payrollPeriod.getEndDate());
        updatePayrollPeriod.setPeriodName(payrollPeriod.getPeriodName());
        updatePayrollPeriod.setPayDate(payrollPeriod.getPayDate());

        return payrollPeriodRepository.save(updatePayrollPeriod);
    }

}
