package com.HRIMS.hrims_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EmployeeRequest {

    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters long.")
    @NotNull(message = "First name cannot be null.")
    private String firstName;
    private String middleName;

    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters long.")
    @NotNull(message = "Last name cannot be null.")
    private String lastName;

    @Size(min = 8, max = 50, message = "User name must be between 8 and 50 characters long.")
    private String userName;

    @Size(min = 8, max = 50, message = "Password must be of 8 characters or more.")
    private String password;

    @Min(value = 1, message = "Salary must be greater than 0.")
    private double salary;

    @NotNull(message = "Last name cannot be null.")
    private String mobileNumber;

    @Email(message = "Invalid Email Address.")
    private String email;

    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate dateOfJoining;
    private Long departmentId;

    private List<AddressDto> addressList;
}
