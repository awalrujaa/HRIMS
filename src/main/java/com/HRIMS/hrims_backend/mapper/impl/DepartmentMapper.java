package com.HRIMS.hrims_backend.mapper.impl;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import com.HRIMS.hrims_backend.mapper.IDepartmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentMapper implements IDepartmentMapper {
    @Override
    public DepartmentDto toDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .departmentName(department.getDepartmentName())
                .departmentCode(department.getDepartmentCode())
                .build();
    }

    @Override
    public Department toDepartmentEntity(DepartmentDto departmentDto) {
        return Department.builder()
                .departmentName(departmentDto.getDepartmentName())
                .departmentCode(departmentDto.getDepartmentCode())
                .build();
    }


}
