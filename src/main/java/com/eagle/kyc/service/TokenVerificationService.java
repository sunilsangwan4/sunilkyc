package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.TokenVerificationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TokenVerification.
 */
public interface TokenVerificationService {

    /**
     * Save a tokenVerification.
     *
     * @param tokenVerificationDTO the entity to save
     * @return the persisted entity
     */
    TokenVerificationDTO save(TokenVerificationDTO tokenVerificationDTO);

    /**
     * Get all the tokenVerifications.
     *
     * @return the list of entities
     */
    List<TokenVerificationDTO> findAll();


    /**
     * Get the "id" tokenVerification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TokenVerificationDTO> findOne(Long id);

    /**
     * Delete the "id" tokenVerification.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
