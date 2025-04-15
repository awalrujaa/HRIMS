package com.HRIMS.hrims_backend.service;

import com.HRIMS.hrims_backend.dto.AttendanceDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AttendanceService {
    // CRUD operations
    AttendanceDto createAttendance(AttendanceDto attendanceDto);

    List<AttendanceDto> viewAllAttendances();

    AttendanceDto viewAttendanceById(Long attendanceId);

    AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDetails);

    String deleteAttendance(Long attendanceId);

}
