package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Find employees by department and sorting
    List<Employee> findByDepartment_Id(Integer departmentId, Sort sort);

    // Find employees between two hire dates and sorting
    List<Employee> findEmployeesByHireDateBetween(
            LocalDate startDate,
            LocalDate endDate,
            Sort sort
    );
}