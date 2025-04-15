package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    // Create an Employee
    EmployeeDto createEmployee(EmployeeDto employeeRequestDto);

    // Get All Employees
    List<EmployeeDto> getAllEmployees();

    // Get Employee By id
    EmployeeDto getEmployeeById(Long id);

    // Update an Employee
    EmployeeDto updateEmployee(Long id, EmployeeDto employee);

    // Delete an Employee
    String deleteEmployee(Long id);

    // Delete All Employees
    String deleteAllEmployees();

}
