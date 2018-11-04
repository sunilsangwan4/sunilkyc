package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationProspectMapper.class, NomineeMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "applicationProspect.id", target = "applicationProspectId")
    @Mapping(source = "nominee.id", target = "nomineeId")
    AddressDTO toDto(Address address);

    @Mapping(source = "applicationProspectId", target = "applicationProspect")
    @Mapping(source = "nomineeId", target = "nominee")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
