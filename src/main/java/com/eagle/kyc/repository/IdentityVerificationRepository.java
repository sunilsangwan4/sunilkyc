package com.eagle.kyc.repository;

import com.eagle.kyc.domain.IdentityVerification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IdentityVerification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentityVerificationRepository extends JpaRepository<IdentityVerification, Long>, JpaSpecificationExecutor<IdentityVerification> {

}
