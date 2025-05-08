package com.HRIMS.hrims_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "First name cannot be null.")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters.")
    private String firstName;


    @Size(max = 50, message = "Middle name must be less than 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Middle name must contain only letters if provided.")
    private String middleName;


    @NotBlank(message = "Last name cannot be null.")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters.")
    private String lastName;


    @NotBlank(message = "User name cannot be null.")
    @Size(min = 8, max = 50, message = "User name must be between 8 and 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "User name must contain only letters, numbers, or underscores.")
    private String userName;


    @NotBlank(message = "Password cannot be null.")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;

    private String roleType;

    @NotNull(message = "Salary cannot be null.")
//    @Min(value = 1, message = "Salary must be greater than 0.")
    private double salary;


    @NotBlank(message = "Mobile number cannot be null.")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid mobile number format.")
    private String mobileNumber;


    @NotBlank(message = "Email cannot be null.")
    @Email(message = "Invalid Email Address.")
    @Size(max = 100, message = "Email must be less than 100 characters long.")
    private String email;


    @NotNull(message = "Date of birth cannot be null.")
    private LocalDate dateOfBirth;


    @NotBlank(message = "Blood group cannot be blank.")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format (e.g., A+, B-, AB+, O-).")
    private String bloodGroup;


    @NotNull(message = "Date of joining cannot be null.")
//    @PastOrPresent(message = "Date of joining cannot be in the future.")
    private LocalDate dateOfJoining;


//    @NotBlank(message = "Department ID cannot be null.")
    private Long departmentId;


    @NotNull(message = "Address list cannot be null.")
    @Size(min = 1, message = "At least one address must be provided.")
    @Valid
    private List<AddressDto> addressList;
}
