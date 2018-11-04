package com.eagle.kyc.repository;

import com.eagle.kyc.domain.PersonalInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonalInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long>, JpaSpecificationExecutor<PersonalInformation> {

}
