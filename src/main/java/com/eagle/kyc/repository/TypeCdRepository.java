package com.eagle.kyc.repository;

import com.eagle.kyc.domain.TypeCd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeCd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCdRepository extends JpaRepository<TypeCd, Long>, JpaSpecificationExecutor<TypeCd> {

}
