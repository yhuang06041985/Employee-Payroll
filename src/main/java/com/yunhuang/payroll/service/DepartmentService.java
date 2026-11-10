package com.yunhuang.payroll.service;

import com.yunhuang.payroll.model.Department;
import com.yunhuang.payroll.repository.DepartmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments()
    {
        return departmentRepository.findAll(Sort.by("departmentName"));
    }

    public Department getDepartment(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department saveDepartment(Department department)
    {
        return  departmentRepository.save(department);
    }

    public Department updateDepartment(Department department, Integer id) {

        Department obj = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));
        obj.setDepartmentCode(department.getDepartmentCode());
        obj.setCostCenter(department.getCostCenter());
        obj.setDepartmentName(department.getDepartmentName());
        return departmentRepository.save(obj);
    }
}
