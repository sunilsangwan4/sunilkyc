package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.ApplicationProspectDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ApplicationProspect.
 */
public interface ApplicationProspectService {

    /**
     * Save a applicationProspect.
     *
     * @param applicationProspectDTO the entity to save
     * @return the persisted entity
     */
    ApplicationProspectDTO save(ApplicationProspectDTO applicationProspectDTO);

    /**
     * Get all the applicationProspects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationProspectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" applicationProspect.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ApplicationProspectDTO> findOne(Long id);

    /**
     * Delete the "id" applicationProspect.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
