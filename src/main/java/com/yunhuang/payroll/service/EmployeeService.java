package com.yunhuang.payroll.service;

import com.yunhuang.payroll.model.Employee;
import com.yunhuang.payroll.model.EmployeeSearchDTO;
import com.yunhuang.payroll.repository.EmployeeRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
//pagination
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
    }

    public List<Employee> getAllEmployeeByDepartment(Integer departmentId) {

        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");

        return employeeRepository.findByDepartment_Id(departmentId, sort);
    }

    public List<Employee> getEmployeesByHireDateBetween(
            LocalDate startDate, LocalDate endDate) {

        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");

        return employeeRepository.findEmployeesByHireDateBetween(
                startDate, endDate, sort);
    }

    public Employee getEmployeeById(Integer id)
    {
        return employeeRepository.findById(id).orElse(null);
    }

    public Integer getDepartmentId(Employee employee)
    {
        return employee.getDetartment().getId();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) {

        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Employee not found: " + id));

        updateEmployee.setFirstName(employee.getFirstName());
        updateEmployee.setLastName(employee.getLastName());
        updateEmployee.setEmail(employee.getEmail());
        updateEmployee.setHireDate(employee.getHireDate());
        updateEmployee.setIsActive(employee.getIsActive());

        return employeeRepository.save(updateEmployee);
    }

    public void removeEmployee(Integer id)
    {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> searchEmployees( String firstName, LocalDate startDate,
            LocalDate endDate, Integer departmentId, Pageable pageable)
    {
        return employeeRepository.searchEmployees(firstName, startDate, endDate, departmentId, pageable);
    }

    public List<EmployeeSearchDTO> getEmployeesName(String firstName, String lastName,
                                                    Integer departmentId)
    {
        return employeeRepository.searchEmployeesName(firstName, lastName, departmentId);
    }
}