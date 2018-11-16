package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.ApplicationProspectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApplicationProspect and its DTO ApplicationProspectDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonalInformationMapper.class, InvestmentPotentialMapper.class, NomineeMapper.class, TradingInfoMapper.class, DepositoryInfoMapper.class, IdentityVerificationMapper.class})
public interface ApplicationProspectMapper extends EntityMapper<ApplicationProspectDTO, ApplicationProspect> {

    @Mapping(source = "personalInformation.id", target = "personalInformationId")
    @Mapping(source = "investmentPotential.id", target = "investmentPotentialId")
    @Mapping(source = "nominee.id", target = "nomineeId")
    @Mapping(source = "tradingInfo.id", target = "tradingInfoId")
    @Mapping(source = "depository.id", target = "depositoryId")
    @Mapping(source = "identityVerification.id", target = "identityVerificationId")
    ApplicationProspectDTO toDto(ApplicationProspect applicationProspect);

    @Mapping(source = "personalInformationId", target = "personalInformation")
    @Mapping(source = "investmentPotentialId", target = "investmentPotential")
    @Mapping(source = "nomineeId", target = "nominee")
    @Mapping(source = "tradingInfoId", target = "tradingInfo")
    @Mapping(source = "depositoryId", target = "depository")
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "bankInformations", ignore = true)
    @Mapping(source = "identityVerificationId", target = "identityVerification")
    ApplicationProspect toEntity(ApplicationProspectDTO applicationProspectDTO);

    default ApplicationProspect fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationProspect applicationProspect = new ApplicationProspect();
        applicationProspect.setId(id);
        return applicationProspect;
    }
}
