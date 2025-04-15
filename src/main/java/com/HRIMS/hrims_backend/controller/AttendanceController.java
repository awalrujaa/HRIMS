package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    AttendanceDto createAttendance(@RequestBody AttendanceDto attendanceDto){
        return attendanceService.createAttendance(attendanceDto);
    }


    @GetMapping
    List<AttendanceDto> viewAllAttendance(){
        return attendanceService.viewAllAttendances();
    }

    @GetMapping("/{attendanceId}")
    AttendanceDto viewAttendanceById(@PathVariable Long attendanceId){
        return attendanceService.viewAttendanceById(attendanceId);
    }

    @PutMapping("/{attendanceId}")
    AttendanceDto updateAttendance(@PathVariable Long attendanceId, @RequestBody AttendanceDto attendanceDetails){
        return attendanceService.updateAttendance(attendanceId, attendanceDetails);
    }

    @DeleteMapping("/{attendanceId}")
    String deleteAttendance(@PathVariable Long attendanceId){
        return attendanceService.deleteAttendance(attendanceId);
    }

}
