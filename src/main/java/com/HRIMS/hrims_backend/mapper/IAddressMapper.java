package com.HRIMS.hrims_backend.mapper;

import com.HRIMS.hrims_backend.dto.response.AddressDto;
import com.HRIMS.hrims_backend.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//@Mapper(
//        /* So This Mapper Can Be Used by Using @AutoWired */
//        componentModel = MappingConstants.ComponentModel.SPRING,
//        /* So the explicitly not mapped field don't show any warning and errors */
//        unmappedTargetPolicy = ReportingPolicy.IGNORE
//)
public interface IAddressMapper {

//    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);


    AddressDto toAddressDto(Address address);

    Address toAddressEntity(AddressDto addressDto);

}
