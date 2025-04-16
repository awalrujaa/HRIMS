package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeRequestDto);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto updateEmployee(Long id, EmployeeDto employee);

    String deleteEmployee(Long id);

    String deleteAllEmployees();

}
