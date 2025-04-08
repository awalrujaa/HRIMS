package com.HRIMS.hrims_backend.dto.response;

import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.entity.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
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

    private DepartmentResponseDto department;

    private AddressResponseDto address;

    @Builder
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentResponseDto {

        private String departmentName;

        private String departmentCode;
    }
    @Builder
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressResponseDto {
        private long addressId;
        private String street;
        private String city;
        private String state;
        private String zipcode;
        private String country;
    }
}
