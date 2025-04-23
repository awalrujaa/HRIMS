package com.HRIMS.hrims_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
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

    @Size(min = 2, max = 50, message = "Department name must be between 2 and 50 characters long.")
    @NotNull(message = "Department name cannot be null.")
    private String name;

    @Size(min = 3, max = 10, message = "Department code must be between 3 and 10 characters long.")
    @NotNull(message = "Department code cannot be null.")
    private String code;

}

