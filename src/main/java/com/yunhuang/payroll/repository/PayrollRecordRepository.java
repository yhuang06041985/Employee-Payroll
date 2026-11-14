package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.EmployeePayrateDto;
import com.yunhuang.payroll.model.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRecordRepository extends JpaRepository<PayrollRecord, Integer> {

    @Query(value = """
    SELECT e.id AS employeeId,
           CONCAT(e.firstName, ' ', e.lastName) AS fullName,
           r.id AS payrateId,
           r.rateType AS rateType,
           r.baseRate AS baseRate
    FROM Employees e
    JOIN Departments d ON e.departmentId = d.id
    JOIN PayRates r ON r.employeeId = e.id
    WHERE
        (:firstName IS NULL
            OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
        AND (:lastName IS NULL
            OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
        AND (:departmentId IS NULL
            OR d.id = :departmentId)
        AND r.effectiveDate <= CAST(GETDATE() AS DATE)
        AND (r.endDate >= CAST(GETDATE() AS DATE)
            OR r.endDate IS NULL)
    """, nativeQuery = true)
    List<EmployeePayrateDto> findEmployeePayrate(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("departmentId") Integer departmentId
    );

}
