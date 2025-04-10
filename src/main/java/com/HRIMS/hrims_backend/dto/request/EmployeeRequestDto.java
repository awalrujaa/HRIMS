package com.HRIMS.hrims_backend.dto.request;


import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.dto.response.AddressDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequestDto {
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
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;

}
