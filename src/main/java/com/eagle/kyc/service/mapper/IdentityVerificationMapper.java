package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.IdentityVerificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IdentityVerification and its DTO IdentityVerificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IdentityVerificationMapper extends EntityMapper<IdentityVerificationDTO, IdentityVerification> {



    default IdentityVerification fromId(Long id) {
        if (id == null) {
            return null;
        }
        IdentityVerification identityVerification = new IdentityVerification();
        identityVerification.setId(id);
        return identityVerification;
    }
}
