package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

//@Mapper(
//        /* So This Mapper Can Be Used by Using @AutoWired */
//        componentModel = MappingConstants.ComponentModel.SPRING,
//        /* So the explicitly not mapped field don't show any warning and errors */
//        unmappedTargetPolicy = ReportingPolicy.IGNORE
//)
public interface IDepartmentMapper {

//    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);


    DepartmentDto toDepartmentDto(Department department);

    Department toDepartmentEntity(DepartmentDto departmentDto);

}
