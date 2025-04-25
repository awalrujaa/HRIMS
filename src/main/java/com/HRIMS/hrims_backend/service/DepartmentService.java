package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.entity.Department;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DepartmentService {

    ApiResponse<DepartmentResponse> createDepartment(DepartmentRequest departmentRequest);

    ApiResponse<List<DepartmentResponse>> bulkUploadDepartments(List<DepartmentRequest> department);

    ApiResponse<PaginatedResponse<DepartmentResponse>> getAllDepartments(int pageNum, int pageSize);

    ApiResponse<DepartmentResponse> getDepartmentById(Long id);

    ApiResponse<DepartmentResponse> updateDepartment(Long id, DepartmentRequest departmentRequest);

    ApiResponse<String> deleteDepartmentById(Long id);

    public ApiResponse<PaginatedResponse<DepartmentResponse>> filterDepartmentsPagination(String name, String code,
                                                                                          String createdBy, String updatedBy,
                                                                                          int pageNum, int pageSize);

    public ApiResponse<String> uploadFile(MultipartFile file);
}
