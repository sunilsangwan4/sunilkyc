package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.GroupCdDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing GroupCd.
 */
public interface GroupCdService {

    /**
     * Save a groupCd.
     *
     * @param groupCdDTO the entity to save
     * @return the persisted entity
     */
    GroupCdDTO save(GroupCdDTO groupCdDTO);

    /**
     * Get all the groupCds.
     *
     * @return the list of entities
     */
    List<GroupCdDTO> findAll();


    /**
     * Get the "id" groupCd.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GroupCdDTO> findOne(Long id);

    /**
     * Delete the "id" groupCd.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
