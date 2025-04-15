package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.PayrollDto;
import com.HRIMS.hrims_backend.entity.Payroll;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface PayrollMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    PayrollDto toPayrollDto(Payroll payroll);

    @Mapping(source = "employeeId", target = "employee.id")
    Payroll toPayrollEntity(PayrollDto payrollDto);
}
