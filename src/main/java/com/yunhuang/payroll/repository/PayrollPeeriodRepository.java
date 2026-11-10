package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.PayrollPeriod;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import com.yunhuang.payroll.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface PayrollPeeriodRepository
    extends JpaRepository<PayrollPeriod, Integer> {
}
