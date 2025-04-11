package com.HRIMS.hrims_backend.mapper.impl;

import com.HRIMS.hrims_backend.dto.response.AddressDto;
import com.HRIMS.hrims_backend.entity.Address;
import com.HRIMS.hrims_backend.mapper.IAddressMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper implements IAddressMapper {

    @Override
    public AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipcode(address.getZipcode())
                .country(address.getCountry())
                .build();
    }

    @Override
    public Address toAddressEntity(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .zipcode(addressDto.getZipcode())
                .country(addressDto.getCountry())
                .build();
    }
}
