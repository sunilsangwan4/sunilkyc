package com.eagle.kyc.repository;

import com.eagle.kyc.domain.EmailVerification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmailVerification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>, JpaSpecificationExecutor<EmailVerification> {

}
