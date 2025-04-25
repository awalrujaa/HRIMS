package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.EmployeeRequest;
import com.HRIMS.hrims_backend.dto.EmployeeResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;

import java.util.List;

public interface EmployeeService {

    ApiResponse<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest);

    ApiResponse<PaginatedResponse<EmployeeResponse>> getAllEmployees(int pageNum, int pageSize);

    ApiResponse<EmployeeResponse> getEmployeeById(Long id);

    ApiResponse<EmployeeResponse> updateEmployee(Long id, EmployeeRequest employeeDetails);

    ApiResponse<String> deleteEmployee(Long id);

    public ApiResponse<PaginatedResponse<EmployeeResponse>> filterEmployees(String email, String middleName, int pageNum, int pageSize);

}
