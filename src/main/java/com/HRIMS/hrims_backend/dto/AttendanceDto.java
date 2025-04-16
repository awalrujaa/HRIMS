package com.HRIMS.hrims_backend.dto;

import com.HRIMS.hrims_backend.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttendanceDto {
    private Long employeeId;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    private AttendanceStatus status;
}
