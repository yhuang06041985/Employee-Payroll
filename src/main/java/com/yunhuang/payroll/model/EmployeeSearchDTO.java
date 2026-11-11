package com.yunhuang.payroll.model;

public class EmployeeSearchDTO {
    private Integer id;
    private String fullName;

    public EmployeeSearchDTO(Integer id, String fullName)
    {
        this.id = id;
        this.fullName = fullName;
    }

    public Integer getId()
    {
        return id;
    }

    public  String getFullName()
    {
        return fullName;
    }

}
