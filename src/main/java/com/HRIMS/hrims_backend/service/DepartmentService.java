package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    List<DepartmentDto> getAllDepartments();
    DepartmentDto getDepartmentById(Long id);
    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);
    String deleteDepartmentById(Long id);
    String deleteDepartments();
}
