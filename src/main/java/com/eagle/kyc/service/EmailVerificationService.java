package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.EmailVerificationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmailVerification.
 */
public interface EmailVerificationService {

    /**
     * Save a emailVerification.
     *
     * @param emailVerificationDTO the entity to save
     * @return the persisted entity
     */
    EmailVerificationDTO save(EmailVerificationDTO emailVerificationDTO);

    /**
     * Get all the emailVerifications.
     *
     * @return the list of entities
     */
    List<EmailVerificationDTO> findAll();


    /**
     * Get the "id" emailVerification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmailVerificationDTO> findOne(Long id);

    /**
     * Delete the "id" emailVerification.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
