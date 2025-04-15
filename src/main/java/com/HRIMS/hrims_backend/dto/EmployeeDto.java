package com.HRIMS.hrims_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private double salary;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate dateOfJoining;
    private Long departmentId;

    private List<AddressDto> addressList;
}
