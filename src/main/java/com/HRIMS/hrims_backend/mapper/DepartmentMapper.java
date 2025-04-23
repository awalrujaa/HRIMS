package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.DepartmentRequest;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toDepartmentEntity(DepartmentRequest departmentRequest);

    DepartmentResponse toDepartmentDTO(Department department);

}
