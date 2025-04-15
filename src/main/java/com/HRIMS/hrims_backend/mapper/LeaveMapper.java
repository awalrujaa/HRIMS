package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeMapper.class })
public interface LeaveMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "leaveType", target = "type")
    @Mapping(source = "leaveStatus", target = "status")
    LeaveDto toLeaveDto(Leave leave);

    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "type", target = "leaveType")
    @Mapping(source = "status", target = "leaveStatus")
    Leave toLeaveEntity(LeaveDto leaveDto);

}
