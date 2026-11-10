package com.yunhuang.payroll.repository;

import java.util.List;
import com.yunhuang.payroll.model.Employee;
import com.yunhuang.payroll.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByDepartment_Id(Integer departmentId, Sort sort);

    List<Employee> findEmployeesByHireDateBetween(
            LocalDate startDate, LocalDate endDate, Sort sort);

}



