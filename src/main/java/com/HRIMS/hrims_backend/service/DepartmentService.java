package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.entity.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();
    Optional<Department> getDepartmentById(Long id);
    Department updateDepartment(Long id, Department department);
    String deleteDepartmentById(Long id);
    String deleteDepartments();
}
