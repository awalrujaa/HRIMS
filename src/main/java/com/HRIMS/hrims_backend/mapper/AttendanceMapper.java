package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface AttendanceMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    AttendanceDto toAttendanceDto(Attendance attendance);

    @Mapping(source = "employeeId", target = "employee.id")
    Attendance toAttendanceEntity(AttendanceDto attendanceDto);
}
