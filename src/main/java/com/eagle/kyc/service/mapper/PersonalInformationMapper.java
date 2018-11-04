package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.PersonalInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonalInformation and its DTO PersonalInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonalInformationMapper extends EntityMapper<PersonalInformationDTO, PersonalInformation> {


    @Mapping(target = "applicationProspect", ignore = true)
    PersonalInformation toEntity(PersonalInformationDTO personalInformationDTO);

    default PersonalInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setId(id);
        return personalInformation;
    }
}
