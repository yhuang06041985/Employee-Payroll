package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // find employee list based on the select condition:
    @Query("""
    SELECT e
    FROM Employee e
    JOIN e.department d
    WHERE
        (:firstName IS NULL
            OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
        AND (:startDate IS NULL
            OR e.hireDate >= :startDate)
        AND (:endDate IS NULL
            OR e.hireDate <= :endDate)
        AND (:departmentId IS NULL
            OR d.id = :departmentId)
    """)
    Page<Employee> searchEmployees(
            @Param("firstName") String firstName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("departmentId") Integer departmentId,
            Pageable pageable
    );

}