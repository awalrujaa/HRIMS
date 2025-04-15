package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.EmployeeDto;
import com.HRIMS.hrims_backend.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface EmployeeMapper {

    @Mapping(source = "addresses", target = "addressList", qualifiedByName = "setAddressesDto")
    EmployeeDto toEmployeeDto(Employee employee);

    @Mapping(source = "addressList", target = "addresses", qualifiedByName = "setAddressesEntity")
    Employee toEmployeeEntity(EmployeeDto employeeDto);
}
