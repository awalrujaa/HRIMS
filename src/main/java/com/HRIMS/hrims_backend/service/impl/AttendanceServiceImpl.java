package com.HRIMS.hrims_backend.service.impl;

import com.HRIMS.hrims_backend.config.AttendanceProperties;
import com.HRIMS.hrims_backend.dto.ApiResponse;
import com.HRIMS.hrims_backend.dto.AttendanceDto;
import com.HRIMS.hrims_backend.dto.PaginatedResponse;
import com.HRIMS.hrims_backend.entity.Attendance;
import com.HRIMS.hrims_backend.entity.Employee;
import com.HRIMS.hrims_backend.enums.AttendanceStatus;
import com.HRIMS.hrims_backend.mapper.AttendanceMapper;
import com.HRIMS.hrims_backend.repository.AttendanceRepository;
import com.HRIMS.hrims_backend.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final AttendanceProperties config;

@Override
    public ApiResponse<AttendanceDto> markRegister(AttendanceDto attendanceDto) {
        LocalDate today = LocalDate.now();

        // Prevent duplicate check-ins
        Optional<Attendance> existing = attendanceRepository.findByEmployeeIdAndDate(attendanceDto.getEmployeeId(), today);
        if (existing.isPresent() && existing.get().getCheckIn() != null) {
            return checkOut(attendanceDto);
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(Employee.builder().
                id(attendanceDto.getEmployeeId()).
                build());
        attendance.setDate(today);
        attendance.setCheckIn(LocalDateTime.now());

        attendanceRepository.save(attendance);
    return new ApiResponse<>(HttpStatus.OK.value(),
            "Checked In Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
            attendanceMapper.toAttendanceDto(attendance), new ArrayList<>());
    }

@Override
    public ApiResponse<AttendanceDto> checkOut(AttendanceDto attendanceDto) {
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(attendanceDto.getEmployeeId(), today)
                .orElseThrow(() -> new IllegalStateException("User has not checked in today."));

        attendance.setCheckOut(LocalDateTime.now());

        attendance.setStatus(getStatus(attendance.getCheckIn(), attendance.getCheckOut()));

        attendanceRepository.save(attendance);
        return new ApiResponse<>(HttpStatus.OK.value(),
            "Checked Out Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                attendanceMapper.toAttendanceDto(attendance), new ArrayList<>());
    }

    @Override
    public AttendanceStatus getStatus(LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime) {
    LocalTime checkInTime = checkInDateTime.toLocalTime();
    LocalTime checkOutTime = checkOutDateTime.toLocalTime();

    LocalTime startTime = LocalTime.parse(config.getStartTime());
    LocalTime graceEnd = startTime.plusMinutes(config.getGracePeriodMinutes());
    LocalTime latestCheckIn = LocalTime.parse(config.getStartTime());
    LocalTime earlyCheckOut = LocalTime.parse(config.getEarlyCheckoutTime());
    LocalTime endTime = LocalTime.parse(config.getEndTime());

    boolean isFirstHalfLeave = checkInTime.isAfter(latestCheckIn);
    boolean isSecondHalfLeave = checkOutTime.isBefore(earlyCheckOut);
    boolean isLate = !isFirstHalfLeave && checkInTime.isAfter(graceEnd);
    boolean isEarlyDepart = checkOutTime.isAfter(earlyCheckOut) & checkOutTime.isBefore(endTime);


    Duration duration = Duration.between(checkOutTime, checkInTime);
    long minutes = duration.toMinutes();

    if (isFirstHalfLeave & isSecondHalfLeave) {
        return AttendanceStatus.ABSENT;
    } else if (isFirstHalfLeave || isSecondHalfLeave) {
        return AttendanceStatus.HALF_DAY_LEAVE;
    } else if (isLate) {
        return AttendanceStatus.LATE;
    } else if (isEarlyDepart) {
        return AttendanceStatus.EARLY_DEPARTURE;
    } else {
        return AttendanceStatus.PRESENT;
    }
    }

    @Override
    public ApiResponse<PaginatedResponse<AttendanceDto>> viewAllAttendances(int pageNum, int pageSize) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Attendance> attendances = attendanceRepository.findAll(pageable);
        // Map Page<Attendance> to a list of AttendanceResponse
        List<AttendanceDto> attendanceDtos = attendances
                .stream()
                .map(attendanceMapper::toAttendanceDto)
                .collect(Collectors.toList());
        // Create a PaginatedResponse
        PaginatedResponse<AttendanceDto> paginatedData = new PaginatedResponse<>(
                attendanceDtos,
                attendances.getTotalPages(),
                attendances.getTotalElements(),
                attendances.getNumber(),
                attendances.getSize()
        );
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Attendances retrieved Successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                paginatedData, new ArrayList<>());
    }

    @Override
    public ApiResponse<AttendanceDto> viewAttendanceById(Long attendanceId) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);
        if(optionalAttendance.isEmpty()){
            throw new RuntimeException("No such attendance request.");
        }
        Attendance attendance = optionalAttendance.get();
        return new ApiResponse<>(HttpStatus.OK.value(),
                "Attendance retrieved successfully.", HttpStatus.OK.name(), LocalDateTime.now(),
                attendanceMapper.toAttendanceDto(attendance), new ArrayList<>());
    }

}


//    @Override
//    public AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDetails) {
//        Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);
//        if(optionalAttendance.isEmpty()){
//            throw new RuntimeException("Attendance not found with id " + attendanceId);
//        }
//        Attendance attendance = optionalAttendance.get();
//        long empId = attendance.getEmployee().getId();
//        attendance.setEmployee(Employee.builder().id(empId).build());
////        attendance.setCheckIn(attendanceDetails.getCheckIn());
////        attendance.setCheckOut(attendanceDetails.getCheckOut());
//        attendanceRepository.save(attendance);
//        return attendanceMapper.toAttendanceDto(attendance);
//    }
//
//    @Override
//    public String deleteAttendance(Long attendanceId) {
//        Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);
//        if(optionalAttendance.isEmpty()){
//            return "Attendance not found with id " + attendanceId;
//        }
//        attendanceRepository.delete(optionalAttendance.get());
//        return "Success";
//    }
//}
