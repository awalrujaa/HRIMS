package com.HRIMS.hrims_backend.dto;

import com.HRIMS.hrims_backend.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private AddressType type;
}
