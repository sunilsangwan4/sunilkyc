package com.eagle.kyc.repository;

import com.eagle.kyc.domain.DepositoryInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepositoryInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositoryInfoRepository extends JpaRepository<DepositoryInfo, Long>, JpaSpecificationExecutor<DepositoryInfo> {

}
