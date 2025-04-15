package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.AddressDto;
import com.HRIMS.hrims_backend.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface AddressMapper {

    AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    AddressDto toAddressDto(Address address);

    Address toAddressEntity(AddressDto addressDto);

    @Named("setAddressesDto")
    default List<AddressDto> setAddressesDto(List<Address> addresses) {
        return addresses.stream()
                .map(this::toAddressDto)
                .collect(Collectors.toList());
    }

    @Named("setAddressesEntity")
    default List<Address> setAddressesEntity(List<AddressDto> addressList) {
        return addressList.stream()
                .map(this::toAddressEntity)
                .collect(Collectors.toList());
    }

}
