package com.eagle.kyc.repository;

import com.eagle.kyc.domain.TokenVerification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TokenVerification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenVerificationRepository extends JpaRepository<TokenVerification, Long>, JpaSpecificationExecutor<TokenVerification> {

}
