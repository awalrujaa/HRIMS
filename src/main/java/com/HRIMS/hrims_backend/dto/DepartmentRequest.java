package com.HRIMS.hrims_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentRequest {

    @NotBlank(message = "Department name is required and cannot be blank.")
    @Size(min = 2, max = 50, message = "Department name must be between 2 and 50 characters long.")
    private String name;

    @NotBlank(message = "Department code is required and cannot be blank.")
    @Size(min = 3, max = 10, message = "Department code must be between 3 and 10 characters long.")
    private String code;

}

