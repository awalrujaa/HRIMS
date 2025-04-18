package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.LeaveDto;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.entity.Leave;
import com.HRIMS.hrims_backend.enums.LeaveStatus;
import com.HRIMS.hrims_backend.mapper.EmployeeMapper;
import com.HRIMS.hrims_backend.mapper.LeaveMapper;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.repository.LeaveRepository;
import com.HRIMS.hrims_backend.service.LeaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveMapper leaveMapper;


    @Override
    public LeaveDto createLeaveRequest(LeaveDto leaveDto) {
        Long empId = leaveDto.getEmployeeId();
        Optional<Employee> employee = employeeRepository.findById(empId);
        if(employee.isEmpty()){
            throw new RuntimeException("Employee doesn't exist with id: " + empId);
        }
        Leave leave = leaveMapper.toLeaveEntity(leaveDto);
        leave.setLeaveStatus(LeaveStatus.PENDING);
        if(leave.getStartDate().isAfter(leave.getEndDate())){
            throw new RuntimeException("Invalid Operation. Start date should be before end date.");
        }
        leaveRepository.save(leave);
        return leaveMapper.toLeaveDto(leave);
    }

    @Override
    public List<LeaveDto> viewLeaveRequests(LocalDate startDate, LocalDate endDate) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Leave> leaveList = leaveRepository.findLeavesBetweenDates(startDate, endDate);
        return leaveList.stream().map(leaveMapper::toLeaveDto).collect(Collectors.toList());
    }

    @Override
    public LeaveDto viewLeaveRequestById(Long leaveId) {
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        if(optionalLeave.isEmpty()){
            throw new RuntimeException("No such leave request.");
        }
        Leave leave = optionalLeave.get();
        return leaveMapper.toLeaveDto(leave);
    }

    @Override
    public LeaveDto updateLeaveRequest(Long leaveId, LeaveDto updatedLeave) {
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        if(optionalLeave.isEmpty()){
            throw new RuntimeException("Leave not found with id " + leaveId);
        }
        Leave leave = optionalLeave.get();
        long empId = leave.getEmployee().getId();
//        Leave leaveEntity = leaveMapper.toLeaveEntity(updatedLeave);
        leave.setEmployee(Employee.builder().id(empId).build());
        leave.setLeaveType(updatedLeave.getType());
        leave.setStartDate(updatedLeave.getStartDate());
        leave.setEndDate(updatedLeave.getEndDate());
        leaveRepository.save(leave);
        return leaveMapper.toLeaveDto(leave);
    }

    @Override
    public String cancelLeaveRequest(Long leaveId) {
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        if (optionalLeave.isEmpty()){
            throw new RuntimeException("Leave not found with id " + leaveId);
        }
        Leave leave = optionalLeave.get();
        leave.setLeaveStatus(LeaveStatus.CANCELLED);
        leaveRepository.save(leave);
        return "Leave with id " + leaveId + " is cancelled.";
    }

    @Override
    public ResponseEntity<Leave> approveOrRejectLeave(Long leaveId) {
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        if (optionalLeave.isEmpty()){
            throw new RuntimeException("Leave not found with id " + leaveId);
        }
        Leave leave = optionalLeave.get();
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
