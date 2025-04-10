package com.HRIMS.hrims_backend.mapper.impl;

import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.dto.response.EmployeeResponseDto;
import com.HRIMS.hrims_backend.entity.Leave;
import com.HRIMS.hrims_backend.mapper.ILeaveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveMapper implements ILeaveMapper {
    private final EmployeeMapper employeeMapper;

    @Override
    public LeaveDto toLeaveDto(Leave leave) {
        return LeaveDto.builder()
                .employeeId(leave.getEmployee().getId())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .build();
    }

    @Override
    public Leave toLeaveEntity(LeaveDto leaveDto) {
        return Leave.builder()
                .employee(employeeMapper.toEmployeeEntity(EmployeeResponseDto.builder()
                                .id(leaveDto.getEmployeeId())
                        .build()))
                .leaveType(leaveDto.getLeaveType())
                .startDate(leaveDto.getStartDate())
                .endDate(leaveDto.getEndDate())
                .leaveStatus(leaveDto.getLeaveStatus())
                .build();
    }
}
