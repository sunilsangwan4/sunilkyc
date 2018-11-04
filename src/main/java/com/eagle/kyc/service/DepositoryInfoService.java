package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.DepositoryInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DepositoryInfo.
 */
public interface DepositoryInfoService {

    /**
     * Save a depositoryInfo.
     *
     * @param depositoryInfoDTO the entity to save
     * @return the persisted entity
     */
    DepositoryInfoDTO save(DepositoryInfoDTO depositoryInfoDTO);

    /**
     * Get all the depositoryInfos.
     *
     * @return the list of entities
     */
    List<DepositoryInfoDTO> findAll();
    /**
     * Get all the DepositoryInfoDTO where ApplicationProspect is null.
     *
     * @return the list of entities
     */
    List<DepositoryInfoDTO> findAllWhereApplicationProspectIsNull();


    /**
     * Get the "id" depositoryInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DepositoryInfoDTO> findOne(Long id);

    /**
     * Delete the "id" depositoryInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
