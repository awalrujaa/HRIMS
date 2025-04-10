package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.entity.Leave;

public interface ILeaveMapper {
    LeaveDto toLeaveDto(Leave leave);
    Leave toLeaveEntity(LeaveDto leaveDto);
}
