package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//@Mapper(componentModel = "spring")
public interface IEmployeeMapper {
//    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

//            @Mapping(source = "department.departmentName", target = "departmentName")
//            @Mapping(source = "address.street", target = "street")
//            @Mapping(source = "address.city", target = "city")
//            @Mapping(source = "address.state", target = "state")
//            @Mapping(source = "address.zipcode", target = "zipcode")
//            @Mapping(source = "address.country", target = "country")

    EmployeeRequestDto toEmployeeRequestDto(Employee employee);
    Employee toEmployeeRequestEntity(EmployeeRequestDto employeeRequestDto);

//    EmployeeResponseDto toEmployeeReResponseDto(Employee employee);
    Employee toEmployeeEntity(EmployeeResponseDto employeeResponseDto);
}
