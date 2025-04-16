package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.entity.Attendance;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.enums.AttendanceStatus;
import com.HRIMS.hrims_backend.exception.ResourceNotFoundException;
import com.HRIMS.hrims_backend.mapper.AttendanceMapper;
import com.HRIMS.hrims_backend.repository.AttendanceRepository;
import com.HRIMS.hrims_backend.repository.EmployeeRepository;
import com.HRIMS.hrims_backend.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public AttendanceDto createCheckIn(AttendanceDto attendanceDto) {
        employeeRepository.findById(attendanceDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No Employee found with id " + attendanceDto.getEmployeeId()));

        Attendance attendance = attendanceMapper.toAttendanceEntity(attendanceDto);
        attendance.setStatus(AttendanceStatus.IN_PROGRESS);
        attendanceRepository.save(attendance);
        return attendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public AttendanceDto createCheckOut(AttendanceDto attendanceDto) {
        employeeRepository.findById(attendanceDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No Employee found with id " + attendanceDto.getEmployeeId()));

        Attendance attendance = attendanceMapper.toAttendanceEntity(attendanceDto);
        Duration duration = Duration.between(attendance.getCheckOut(), attendance.getCheckIn());
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        attendance.setStatus(AttendanceStatus.IN_PROGRESS);
        attendanceRepository.save(attendance);
        return attendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public List<AttendanceDto> viewAllAttendances() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Attendance> attendanceList = attendanceRepository.findAll();
        return attendanceList.stream().map(attendanceMapper::toAttendanceDto).collect(Collectors.toList());
    }

    @Override
    public AttendanceDto viewAttendanceById(Long attendanceId) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);
        if(optionalAttendance.isEmpty()){
            throw new RuntimeException("No such attendance request.");
        }
        Attendance attendance = optionalAttendance.get();
        return attendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDetails) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);
        if(optionalAttendance.isEmpty()){
            throw new RuntimeException("Attendance not found with id " + attendanceId);
        }
        Attendance attendance = optionalAttendance.get();
        long empId = attendance.getEmployee().getId();
        attendance.setEmployee(Employee.builder().id(empId).build());
        attendance.setCheckIn(attendanceDetails.getCheckIn());
        attendance.setCheckOut(attendanceDetails.getCheckOut());
        attendanceRepository.save(attendance);
        return attendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public String deleteAttendance(Long attendanceId) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);
        if(optionalAttendance.isEmpty()){
            return "Attendance not found with id " + attendanceId;
        }
        attendanceRepository.delete(optionalAttendance.get());
        return "Success";
    }
}
