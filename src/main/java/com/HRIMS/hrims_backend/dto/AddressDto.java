package com.HRIMS.hrims_backend.dto;

import com.HRIMS.hrims_backend.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    @NotBlank(message = "Street cannot be blank.")
    @Size(max = 100, message = "Street must be less than 100 characters long.")
    private String street;


    @NotBlank(message = "City cannot be blank.")
    @Size(max = 50, message = "City must be less than 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "City must contain only letters, spaces, or hyphens.")
    private String city;


    @NotBlank(message = "State cannot be blank.")
    @Size(max = 50, message = "State must be less than 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "State must contain only letters, spaces, or hyphens.")
    private String state;


    @NotBlank(message = "Zipcode cannot be blank.")
    @Pattern(regexp = "^[0-9]{5}(-[0-9]{4})?$", message = "Invalid zipcode format (e.g., 12345 or 12345-6789).")
    private String zipcode;


    @NotBlank(message = "Country cannot be blank.")
    @Size(max = 50, message = "Country must be less than 50 characters long.")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "Country must contain only letters, spaces, or hyphens.")
    private String country;


    @NotNull(message = "Address type cannot be null.")
    private AddressType type;

}
