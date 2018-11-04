package com.eagle.kyc.repository;

import com.eagle.kyc.domain.InvestmentPotential;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvestmentPotential entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvestmentPotentialRepository extends JpaRepository<InvestmentPotential, Long>, JpaSpecificationExecutor<InvestmentPotential> {

}
