package com.yunhuang.payroll.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "Departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToMany(mappedBy = "department")
    List<Employee> employees;

    @NotBlank(message = "Department name is required")
    private String departmentName;

    @Column(name = "CostCenter", nullable = false)
    private String costCenter;

    private String departmentCode;

    public Integer getId()
    {
        return Id;
    }

    public void setDepartmentId(Integer Id) {
        this.Id = Id;
    }

    public List<Employee> getAccounts(List<Employee> employees)
    {
        return employees;
    }

    public void setAccounts(List<Employee> employees)
    {
        this.employees = employees;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getCostCenter()
    {
        return costCenter;
    }

    public void setDepartmentCode(String departmentCode)
    {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentCode()
    {
        return this.departmentCode;
    }

    public void setCostCenter(String costCenter)
    {
        this.costCenter = costCenter;
    }
}
