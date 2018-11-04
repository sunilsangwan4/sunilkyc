package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.InvestmentPotentialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InvestmentPotential and its DTO InvestmentPotentialDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvestmentPotentialMapper extends EntityMapper<InvestmentPotentialDTO, InvestmentPotential> {


    @Mapping(target = "applicationProspect", ignore = true)
    InvestmentPotential toEntity(InvestmentPotentialDTO investmentPotentialDTO);

    default InvestmentPotential fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvestmentPotential investmentPotential = new InvestmentPotential();
        investmentPotential.setId(id);
        return investmentPotential;
    }
}
