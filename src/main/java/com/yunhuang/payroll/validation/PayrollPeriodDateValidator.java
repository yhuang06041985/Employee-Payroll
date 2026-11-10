package com.yunhuang.payroll.validation;

import com.yunhuang.payroll.model.PayrollPeriod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PayrollPeriodDateValidator
        implements ConstraintValidator<ValidPayrollPeriodDates, PayrollPeriod> {

    @Override
    public boolean isValid(
            PayrollPeriod period,
            ConstraintValidatorContext context) {

        if (period == null ||
                period.getStartDate() == null ||
                period.getEndDate() == null ||
                period.getPayDate() == null) {
            return true;
        }

        boolean valid = true;

        context.disableDefaultConstraintViolation();

        if (!period.getEndDate().isAfter(period.getStartDate())) {
            context.buildConstraintViolationWithTemplate(
                            "End Date must be greater than Start Date")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();

            valid = false;
        }

        if (!period.getPayDate().isAfter(period.getEndDate())) {
            context.buildConstraintViolationWithTemplate(
                            "Pay Date must be greater than End Date")
                    .addPropertyNode("payDate")
                    .addConstraintViolation();

            valid = false;
        }

        return valid;
    }
}