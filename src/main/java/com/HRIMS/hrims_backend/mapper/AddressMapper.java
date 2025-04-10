package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.response.AddressDto;
import com.HRIMS.hrims_backend.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toAddressDto(Address address);

    Address toAddressEntity(AddressDto addressDto);

}
