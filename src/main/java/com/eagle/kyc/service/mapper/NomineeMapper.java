package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.NomineeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Nominee and its DTO NomineeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NomineeMapper extends EntityMapper<NomineeDTO, Nominee> {


    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "applicationProspect", ignore = true)
    Nominee toEntity(NomineeDTO nomineeDTO);

    default Nominee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nominee nominee = new Nominee();
        nominee.setId(id);
        return nominee;
    }
}
