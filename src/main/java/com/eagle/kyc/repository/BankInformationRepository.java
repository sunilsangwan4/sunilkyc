package com.eagle.kyc.repository;

import com.eagle.kyc.domain.BankInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankInformationRepository extends JpaRepository<BankInformation, Long>, JpaSpecificationExecutor<BankInformation> {

}
