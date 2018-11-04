package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.BankInformationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BankInformation.
 */
public interface BankInformationService {

    /**
     * Save a bankInformation.
     *
     * @param bankInformationDTO the entity to save
     * @return the persisted entity
     */
    BankInformationDTO save(BankInformationDTO bankInformationDTO);

    /**
     * Get all the bankInformations.
     *
     * @return the list of entities
     */
    List<BankInformationDTO> findAll();


    /**
     * Get the "id" bankInformation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BankInformationDTO> findOne(Long id);

    /**
     * Delete the "id" bankInformation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
