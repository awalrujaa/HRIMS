package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.ApiResponse;

public interface DepartmentService {

    ApiResponse<DepartmentResponse> createDepartment(DepartmentRequest departmentRequest);

    ApiResponse<PaginatedResponse<DepartmentResponse>> getAllDepartments(int pageNum, int pageSize);

    ApiResponse<DepartmentResponse> getDepartmentById(Long id);

    ApiResponse<DepartmentResponse> updateDepartment(Long id, DepartmentRequest departmentRequest);

    ApiResponse<String> deleteDepartmentById(Long id);

}
