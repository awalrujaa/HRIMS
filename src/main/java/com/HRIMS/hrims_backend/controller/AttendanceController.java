package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/mark-register")
    ApiResponse<AttendanceDto> markRegister(@RequestBody AttendanceDto attendanceDto){
        return attendanceService.markRegister(attendanceDto);
    }

    @GetMapping
    ApiResponse<PaginatedResponse<AttendanceDto>> viewAllAttendance(@RequestParam(defaultValue = "0") int pageNum,
                                                                   @RequestParam(defaultValue = "10") int pageSize){
        return attendanceService.viewAllAttendances(pageNum, pageSize);
    }

    @GetMapping("/{attendanceId}")
    ApiResponse<AttendanceDto> viewAttendanceById(@PathVariable Long attendanceId){
        return attendanceService.viewAttendanceById(attendanceId);
    }

//    @PutMapping("/{attendanceId}")
//    AttendanceDto updateAttendance(@PathVariable Long attendanceId, @RequestBody AttendanceDto attendanceDetails){
//        return attendanceService.updateAttendance(attendanceId, attendanceDetails);
//    }
//
//    @DeleteMapping("/{attendanceId}")
//    String deleteAttendance(@PathVariable Long attendanceId){
//        return attendanceService.deleteAttendance(attendanceId);
//    }

}
