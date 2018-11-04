package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.IdentityVerificationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing IdentityVerification.
 */
public interface IdentityVerificationService {

    /**
     * Save a identityVerification.
     *
     * @param identityVerificationDTO the entity to save
     * @return the persisted entity
     */
    IdentityVerificationDTO save(IdentityVerificationDTO identityVerificationDTO);

    /**
     * Get all the identityVerifications.
     *
     * @return the list of entities
     */
    List<IdentityVerificationDTO> findAll();


    /**
     * Get the "id" identityVerification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IdentityVerificationDTO> findOne(Long id);

    /**
     * Delete the "id" identityVerification.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
