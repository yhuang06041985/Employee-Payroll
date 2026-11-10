package com.yunhuang.payroll.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PayrollPeriodDateValidator.class)
@Documented
public @interface ValidPayrollPeriodDates {

    String message() default "Invalid payroll period dates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}