package com.HRIMS.hrims_backend.dto;

import com.HRIMS.hrims_backend.enums.LeaveStatus;
import com.HRIMS.hrims_backend.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveDto {

    private Long employeeId;
    private LeaveType type;
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private LeaveStatus status;
}
