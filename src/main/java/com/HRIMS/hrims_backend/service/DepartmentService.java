package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import java.util.List;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long id);

    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);

    String deleteDepartmentById(Long id);

    String deleteDepartments();
}
