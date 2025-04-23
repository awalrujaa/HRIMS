package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.entity.Leave;
import com.HRIMS.hrims_backend.enums.LeaveStatus;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import com.HRIMS.hrims_backend.mapper.LeaveMapper;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.repository.LeaveRepository;
import com.HRIMS.hrims_backend.service.LeaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveMapper leaveMapper;


    @Override
    public ApiResponse<LeaveDto> createLeaveRequest(LeaveDto leaveDto) {
        Long empId = leaveDto.getEmployeeId();
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with id: " + empId));

        Leave leave = leaveMapper.toLeaveEntity(leaveDto);
        leave.setLeaveStatus(LeaveStatus.PENDING);
        if(leave.getStartDate().isAfter(leave.getEndDate())){
            throw new RuntimeException("Invalid Operation. Start date should be before end date.");
        }
        leaveRepository.save(leave);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Created Department Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                leaveMapper.toLeaveDto(leave), new ArrayList<>());
    }

    @Override
    public ApiResponse<PaginatedResponse<LeaveDto>> viewLeaveRequests(LocalDate startDate, LocalDate endDate,
                                                                      int pageNum, int pageSize) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Leave> leaves = leaveRepository.findLeavesBetweenDates(startDate, endDate, pageable);
        List<LeaveDto> leaveResponses = leaves
                .stream()
                .map(leaveMapper::toLeaveDto)
                .collect(Collectors.toList());

        PaginatedResponse<LeaveDto> paginatedData = new PaginatedResponse<>(
                leaveResponses,
                leaves.getTotalPages(),
                leaves.getTotalElements(),
                leaves.getNumber(),
                leaves.getSize()
        );
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Retrieved Leaves Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }

    @Override
    public ApiResponse<LeaveDto> viewLeaveRequestById(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not exist with id " + id));

        return new ApiResponse<>(HttpStatus.OK.value(),
                "Leave retrieved successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                leaveMapper.toLeaveDto(leave), new ArrayList<>());
    }

    @Override
    public ApiResponse<LeaveDto> updateLeaveRequest(Long id, LeaveDto updatedLeave) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not exist with id " + id));

        long empId = leave.getEmployee().getId();
        leave.setEmployee(Employee.builder().id(empId).build());
        leave.setLeaveType(updatedLeave.getType());
        leave.setStartDate(updatedLeave.getStartDate());
        leave.setEndDate(updatedLeave.getEndDate());
        leaveRepository.save(leave);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Department updated successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                leaveMapper.toLeaveDto(leave), new ArrayList<>());
    }

    @Override
    public ApiResponse<String> cancelLeaveRequest(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not exist with id " + id));

        leave.setLeaveStatus(LeaveStatus.CANCELLED);
        leaveRepository.save(leave);
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Leave Cancelled Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                "Leave with id " + id + " is cancelled.", new ArrayList<>());
    }

    @Override
    public ResponseEntity<Leave> approveOrRejectLeave(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not exist with id " + id));

        if(leave.getLeaveStatus() == LeaveStatus.APPROVED || leave.getLeaveStatus() == LeaveStatus.REJECTED){
            throw new RuntimeException("This leave has already been processed.");
        }
        leave.setLeaveStatus(LeaveStatus.CANCELLED);
        leaveRepository.save(leave);
        sendLeaveNotification();
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }

    public String sendLeaveNotification() {
        return "Notification";
    }

    @Override
    public String checkLeaveBalance() {
        return "";
    }
}
