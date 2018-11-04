package com.eagle.kyc.repository;

import com.eagle.kyc.domain.ApplicationProspect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationProspect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationProspectRepository extends JpaRepository<ApplicationProspect, Long>, JpaSpecificationExecutor<ApplicationProspect> {

}
