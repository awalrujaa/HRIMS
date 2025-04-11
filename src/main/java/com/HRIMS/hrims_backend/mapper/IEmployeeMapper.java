package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.request.EmployeeRequestDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface IEmployeeMapper {

    EmployeeRequestDto toEmployeeRequestDto(Employee employee);
    Employee toEmployeeRequestEntity(EmployeeRequestDto employeeRequestDto);

//    EmployeeResponseDto toEmployeeReResponseDto(Employee employee);
    Employee toEmployeeEntity(EmployeeResponseDto employeeResponseDto);
}
