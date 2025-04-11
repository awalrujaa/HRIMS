package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.entity.Leave;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface LeaveService {
    LeaveDto createLeaveRequest(LeaveDto leaveDto);
    List<LeaveDto> viewLeaveRequests(LocalDate startDate, LocalDate endDate);
    LeaveDto viewLeaveRequestById(Long leaveId);
    LeaveDto updateLeaveRequest(Long leaveId, LeaveDto updatedLeave);
    String cancelLeaveRequest(Long leaveId);
    ResponseEntity<Leave> approveOrRejectLeave(Long leaveId);
//    String sendLeaveNotification();
//    String checkLeaveBalance();
}
