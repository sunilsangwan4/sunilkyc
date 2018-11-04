package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.NomineeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Nominee.
 */
public interface NomineeService {

    /**
     * Save a nominee.
     *
     * @param nomineeDTO the entity to save
     * @return the persisted entity
     */
    NomineeDTO save(NomineeDTO nomineeDTO);

    /**
     * Get all the nominees.
     *
     * @return the list of entities
     */
    List<NomineeDTO> findAll();
    /**
     * Get all the NomineeDTO where ApplicationProspect is null.
     *
     * @return the list of entities
     */
    List<NomineeDTO> findAllWhereApplicationProspectIsNull();


    /**
     * Get the "id" nominee.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NomineeDTO> findOne(Long id);

    /**
     * Delete the "id" nominee.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
