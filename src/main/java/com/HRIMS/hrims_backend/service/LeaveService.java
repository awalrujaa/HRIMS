package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.DepartmentResponse;
import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.entity.Leave;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface LeaveService {
    ApiResponse<LeaveDto> createLeaveRequest(LeaveDto leaveDto);
    ApiResponse<PaginatedResponse<LeaveDto>> viewLeaveRequests(LocalDate startDate, LocalDate endDate,
                                                               int pageNum, int PageSize);
    ApiResponse<LeaveDto> viewLeaveRequestById(Long leaveId);
    ApiResponse<LeaveDto> updateLeaveRequest(Long leaveId, LeaveDto updatedLeave);
    ApiResponse<String> cancelLeaveRequest(Long leaveId);
    ResponseEntity<Leave> approveOrRejectLeave(Long leaveId);
    String sendLeaveNotification();
    String checkLeaveBalance();
}
