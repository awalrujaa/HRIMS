package com.HRIMS.hrims_backend.dto;

import com.HRIMS.hrims_backend.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
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
public class PayrollDto {
    private Long employeeId;

    private double basicSalary;
    private double dashainAllowance;
    private double bonus;
    private double performanceBonus;
    private double cit;
    private double pf;
    private double ssf;
    private double tax;
    private double others;

    private LocalDateTime payDate;
}
