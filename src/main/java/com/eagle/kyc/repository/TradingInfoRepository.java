package com.eagle.kyc.repository;

import com.eagle.kyc.domain.TradingInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TradingInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradingInfoRepository extends JpaRepository<TradingInfo, Long>, JpaSpecificationExecutor<TradingInfo> {

}
