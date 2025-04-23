package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.EmployeeRequest;
import com.HRIMS.hrims_backend.dto.EmployeeResponse;
import com.HRIMS.hrims_backend.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface EmployeeMapper {

    @Mapping(source = "addresses", target = "addressList", qualifiedByName = "setAddressesDto")
    EmployeeResponse toEmployeeDto(Employee employee);

    @Mapping(source = "addressList", target = "addresses", qualifiedByName = "setAddressesEntity")
    Employee toEmployeeEntity(EmployeeRequest employeeRequest);

}
