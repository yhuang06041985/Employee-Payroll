package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.PayRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRateRepository extends JpaRepository<PayRate, Integer> {

}
