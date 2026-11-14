package com.yunhuang.payroll.model;

import jakarta.persistence.*;
import com.yunhuang.payroll.model.Department;
import java.util.List;
import java.time.LocalDate;
import com.yunhuang.payroll.model.PayRate;
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "employee")
    List<PayRate> payRates;

    @OneToMany(mappedBy = "employee")
    List<PayrollRecord> payrollRecords;

    private LocalDate hireDate;
    private boolean isActive;

    public Integer getId()
    {
        return Id;
    }

    public void setId(Integer Id)
    {
        this.Id = Id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public LocalDate getHireDate()
    {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate)
    {
        this.hireDate = hireDate;
    }

    public boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Department getDetartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

}
