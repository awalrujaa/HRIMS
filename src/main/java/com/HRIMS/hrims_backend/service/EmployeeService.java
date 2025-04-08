package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    // Create an Employee
    Employee createEmployee(Employee employee);

    // Get All Employees
    List<EmployeeResponseDto> getAllEmployees();

    // Get Employee By id
    Optional<Employee> getEmployeeById(Long id);

    // Update an Employee
    Employee updateEmployee(Long id, Employee employee);

    // Delete an Employee
    String deleteEmployee(Long id);

    // Delete All Employees
    String deleteAllEmployees();

}
