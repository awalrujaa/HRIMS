package com.HRIMS.hrims_backend.controller;

import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/request")
    LeaveDto createLeaveRequest(@RequestBody LeaveDto leaveDto){

        return leaveService.createLeaveRequest(leaveDto);
    }

    @GetMapping("/filter")
    List<LeaveDto> viewLeaveRequests(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        if(startDate.isAfter(endDate)){
            throw new RuntimeException("End date can not be before start date.");
        }
        return leaveService.viewLeaveRequests(startDate, endDate);
    }

    @GetMapping("/{leaveId}")
    LeaveDto viewLeaveById(@PathVariable Long leaveId){
        return leaveService.viewLeaveRequestById(leaveId);
    }

    @PutMapping("/{leaveId}")
    LeaveDto updateLeave(@PathVariable Long leaveId, @RequestBody LeaveDto updatedLeave){
        return leaveService.updateLeaveRequest(leaveId, updatedLeave);
    }

    @DeleteMapping("/cancel/{leaveId}")
    String cancelLeaveRequest(@PathVariable Long leaveId){
        return leaveService.cancelLeaveRequest(leaveId);
    }

}
