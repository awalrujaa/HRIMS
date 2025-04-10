package com.HRIMS.hrims_backend.dto.response;

import com.HRIMS.hrims_backend.dto.DepartmentDto;
import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponseDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private double salary;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate dateOfJoining;

    private DepartmentDto department;

    private AddressDto address;
//
//    @Builder
//    @Setter
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class DepartmentResponseDto {
//
//        private String departmentName;
//
//        private String departmentCode;
//    }
//    @Builder
//    @Setter
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class AddressResponseDto {
//        private long addressId;
//        private String street;
//        private String city;
//        private String state;
//        private String zipcode;
//        private String country;
//    }
}
