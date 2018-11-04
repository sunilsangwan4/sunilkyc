package com.eagle.kyc.repository;

import com.eagle.kyc.domain.GroupCd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GroupCd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupCdRepository extends JpaRepository<GroupCd, Long>, JpaSpecificationExecutor<GroupCd> {

}
