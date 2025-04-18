package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService {

    AttendanceDto checkIn(AttendanceDto attendanceDto);

    AttendanceDto checkOut(AttendanceDto attendanceDto);

    public AttendanceStatus getStatus(LocalDateTime checkInTime, LocalDateTime checkOutTime);

    List<AttendanceDto> viewAllAttendances();

    AttendanceDto viewAttendanceById(Long attendanceId);
//
//    AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDetails);
//
//    String deleteAttendance(Long attendanceId);

}
