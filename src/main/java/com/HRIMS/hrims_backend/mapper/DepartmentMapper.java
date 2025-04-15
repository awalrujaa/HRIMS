package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto toDepartmentDto(Department department);

    Department toDepartmentEntity(DepartmentDto departmentDto);

}
