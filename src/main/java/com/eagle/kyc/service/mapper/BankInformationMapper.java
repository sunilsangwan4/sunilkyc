package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.BankInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BankInformation and its DTO BankInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationProspectMapper.class})
public interface BankInformationMapper extends EntityMapper<BankInformationDTO, BankInformation> {

    @Mapping(source = "applicationProspect.id", target = "applicationProspectId")
    BankInformationDTO toDto(BankInformation bankInformation);

    @Mapping(source = "applicationProspectId", target = "applicationProspect")
    BankInformation toEntity(BankInformationDTO bankInformationDTO);

    default BankInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        BankInformation bankInformation = new BankInformation();
        bankInformation.setId(id);
        return bankInformation;
    }
}
