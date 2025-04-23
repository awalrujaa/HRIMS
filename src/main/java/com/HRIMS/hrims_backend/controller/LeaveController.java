package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/request")
    ApiResponse<LeaveDto> createLeaveRequest(@RequestBody LeaveDto leaveDto){

        return leaveService.createLeaveRequest(leaveDto);
    }

    @GetMapping("/filter")
    ApiResponse<PaginatedResponse<LeaveDto>> viewLeaveRequests(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
                                                                                  @RequestParam(defaultValue = "0") int pageNum,
                                                                                  @RequestParam(defaultValue = "10") int pageSize){
        if(startDate.isAfter(endDate)){
            throw new RuntimeException("End date can not be before start date.");
        }
        return leaveService.viewLeaveRequests(startDate, endDate, pageNum, pageSize);
    }

    @GetMapping("/{leaveId}")
    ApiResponse<LeaveDto> viewLeaveById(@PathVariable Long leaveId){
        return leaveService.viewLeaveRequestById(leaveId);
    }

    @PutMapping("/{leaveId}")
    ApiResponse<LeaveDto> updateLeave(@PathVariable Long leaveId, @RequestBody LeaveDto updatedLeave){
        return leaveService.updateLeaveRequest(leaveId, updatedLeave);
    }

    @DeleteMapping("/cancel/{leaveId}")
    ApiResponse<String> cancelLeaveRequest(@PathVariable Long leaveId){
        return leaveService.cancelLeaveRequest(leaveId);
    }

}
