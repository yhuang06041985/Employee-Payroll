package com.yunhuang.payroll.repository;

import com.yunhuang.payroll.model.EmployeePayrateDto;
import com.yunhuang.payroll.model.PayrollRecord;
import com.yunhuang.payroll.model.PayrollSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

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

      /*  @Query("SELECT NEW com.example.dto.PayrollSummaryDto(" +
                "r.id, " +
                "CONCAT(e.firstName, ' ', e.lastName), " +
                "d.departmentCode, " +
                "r.grossPay, " +
                "r.taxAmount, " +
                "r.otherDeductions, " +
                "r.netPay) " +
                "FROM PayrollRecord r " +
                "JOIN r.employee e " +
                "JOIN e.department d " +
                "JOIN r.payrollPeriod p " +
                "WHERE p.startDate <= CURRENT_TIMESTAMP " +
                "AND p.endDate > CURRENT_TIMESTAMP " +
                "AND (:firstName IS NULL OR e.firstName LIKE CONCAT('%', :firstName, '%')) " +
                "AND (:lastName IS NULL OR e.lastName LIKE CONCAT('%', :lastName, '%')) " +
                "AND (:departmentId IS NULL OR d.id = :departmentId)")
        List<PayrollSummaryDto> findCurrentPayrollRecords(
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("departmentId") Integer departmentId
        ); */

    @Query(value= """
    Select e.FirstName + ' ' + e.LastName as fullName,
           d.DepartmentCode as departmentCode,
           r.GrossPay as grossPay,
           r.TaxAmount as taxAmount,
           r.OtherDeductions as otherDeductions,
           r.NetPay as netPay
    from PayrollRecords r
    Inner Join Employees e
    On r.EmployeeId = e.id
    Inner Join Departments d
    ON e.DepartmentID = d.id
    Inner Join PayrollPeriods p
    on r.PayrollPeriodId = p.id
    Where p.StartDate <= GetDate()
      and p.EndDate > GetDate()
      And (:firstName is null oR e.firstName like '%' + :firstName + '%')
      And (:lastName is null oR e.LastName like '%' + :LastName + '%')
      And (:Department is null Or d.id = :DepartmentId)
""", nativeQuery = true)
    List<PayrollSummaryDto> findPayrollSummary(String firstName, String lastName, Integer departmentId);

   @Query(value= """
   Select e.FirstName + ' ' + e.LastName as fullName,
          d.DepartmentCode as departmentCode,
          r.GrossPay as grossPay,
          r.TaxAmount as taxAmount,
          r.OtherDeductions as otherDeductions,
          r.NetPay as netPay
   from PayrollRecords r
   Inner Join Employees e
   On r.EmployeeId = e.id
   Inner Join Departments d
   ON e.DepartmentID = d.id
   Inner Join PayrollPeriods p
   on r.PayrollPeriodId = p.id
   And (:firstName is null oR e.firstName like '%' + :firstName + '%')
      And (:lastName is null oR e.LastName like '%' + :LastName + '%')
   order by e.LastName, e.FirstName,
     p.startdate desc
""", nativeQuery = true)
    List<PayrollSummaryDto> findDetailSummaryPayroll(String firstName, String lastName);

    }


