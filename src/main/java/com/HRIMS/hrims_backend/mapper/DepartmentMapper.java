package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

//    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);


    DepartmentDto toDepartmentDto(Department department);

    Department toDepartmentEntity(DepartmentDto departmentDto);

    List<DepartmentDto> toDepartmentDtoList(List<Department> departments);
}
