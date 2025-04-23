package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService {

    ApiResponse<AttendanceDto> markRegister(AttendanceDto attendanceDto);

    ApiResponse<AttendanceDto> checkOut(AttendanceDto attendanceDto);

    public AttendanceStatus getStatus(LocalDateTime checkInTime, LocalDateTime checkOutTime);

    ApiResponse<PaginatedResponse<AttendanceDto>> viewAllAttendances(int pageNum, int pageSize);

    ApiResponse<AttendanceDto> viewAttendanceById(Long attendanceId);
//
//    AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDetails);
//
//    String deleteAttendance(Long attendanceId);

}
