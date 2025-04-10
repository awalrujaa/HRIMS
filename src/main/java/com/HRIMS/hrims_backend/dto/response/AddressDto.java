package com.HRIMS.hrims_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
