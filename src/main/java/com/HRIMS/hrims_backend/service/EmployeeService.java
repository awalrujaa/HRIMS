package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;

import java.util.List;

public interface EmployeeService {
    // Create an Employee
    EmployeeRequestDto createEmployee(EmployeeRequestDto employeeRequestDto);

    // Get All Employees
    List<EmployeeRequestDto> getAllEmployees();

    // Get Employee By id
    EmployeeRequestDto getEmployeeById(Long id);

    // Update an Employee
    EmployeeRequestDto updateEmployee(Long id, EmployeeRequestDto employee);

    // Delete an Employee
    String deleteEmployee(Long id);

    // Delete All Employees
    String deleteAllEmployees();

}
