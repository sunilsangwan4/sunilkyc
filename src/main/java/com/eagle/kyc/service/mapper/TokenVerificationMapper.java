package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.TokenVerificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TokenVerification and its DTO TokenVerificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TokenVerificationMapper extends EntityMapper<TokenVerificationDTO, TokenVerification> {



    default TokenVerification fromId(Long id) {
        if (id == null) {
            return null;
        }
        TokenVerification tokenVerification = new TokenVerification();
        tokenVerification.setId(id);
        return tokenVerification;
    }
}
