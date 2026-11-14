package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.EmployeePayrateDto;
import com.yunhuang.payroll.model.PayRateDto;
import com.yunhuang.payroll.model.PayRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayRateRepository
        extends JpaRepository<PayRate, Integer> {

    @Query(value = """

            WITH rate AS
        (
            SELECT *,
                   ROW_NUMBER() OVER
                   (
                       PARTITION BY EmployeeId
                       ORDER BY EffectiveDate DESC, Id DESC
                   ) AS rn
            FROM PayRates
        )
        SELECT
            r.id as id,                    
            CONCAT(e.FirstName, ' ', e.LastName) AS fullName,
            d.DepartmentCode AS departmentCode,
            r.rateType AS payType,
            r.baseRate AS baseRate,
            r.effectiveDate AS effectiveDate,
            r.enddate as endDate
        FROM rate r
        INNER JOIN Employees e
            ON e.Id = r.EmployeeId
        INNER JOIN Departments d
            ON d.Id = e.DepartmentId
        WHERE r.rn = 1
          AND (:departmentId IS NULL OR d.Id = :departmentId)
          AND (:firstName IS NULL OR e.firstName like '%' + :firstName + '%')
          AND (:lastName IS NULL Or e.lastName like '%' + :lastName + '%')
        ORDER BY e.LastName, e.FirstName
        """, nativeQuery = true)
    List<PayRateDto> findLatestPayRatesByDepartment(
            @Param("departmentId") Integer departmentId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );

    @Query(value = """
        SELECT
            r.id as id, 
            e.FirstName + ' ' + e.LastName AS FullName,
            d.DepartmentCode AS DepartmentCode,
            r.RateType AS RateType,
            r.BaseRate AS BaseRate,
            r.EffectiveDate AS EffectiveDate,
            r.EndDate AS EndDate
        FROM PayRates r
        INNER JOIN Employees e ON r.EmployeeId = e.Id
        INNER JOIN Departments d ON e.DepartmentId = d.Id
        WHERE e.FirstName LIKE '%' + :firstName + '%'
          AND e.LastName LIKE '%' + :lastName + '%'
          AND (:departmentId IS NULL OR d.Id = :departmentId)
        ORDER BY e.LastName, e.FirstName, r.EffectiveDate DESC
        """, nativeQuery = true)
    List<PayRateDto> findPayRateByEmployee(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("departmentId") Integer departmentId);

    @Query(value= """
        select e.id as employeeId,\s
        		e.firstName + ' ' + e.lastName as fullName,
        		r.id as payrateId,
        		r.ratetype as rateType,
        		r.baseRate as baseRate
        from employees e
        inner join payrates r
        on e.id = r.employeeid
        where r.effectiveDate <= getDate()
          and (r.enddate > getDate() Or r.endDate is null)
          and e.id = :employeeId
    """, nativeQuery = true)
    List<EmployeePayrateDto> findEmployeePayrate(Integer employeeId);

    }
