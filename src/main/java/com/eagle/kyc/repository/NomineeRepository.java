package com.eagle.kyc.repository;

import com.eagle.kyc.domain.Nominee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nominee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long>, JpaSpecificationExecutor<Nominee> {

}
