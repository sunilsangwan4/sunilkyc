package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.EmailVerificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmailVerification and its DTO EmailVerificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailVerificationMapper extends EntityMapper<EmailVerificationDTO, EmailVerification> {



    default EmailVerification fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setId(id);
        return emailVerification;
    }
}
