package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.InvestmentPotentialDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing InvestmentPotential.
 */
public interface InvestmentPotentialService {

    /**
     * Save a investmentPotential.
     *
     * @param investmentPotentialDTO the entity to save
     * @return the persisted entity
     */
    InvestmentPotentialDTO save(InvestmentPotentialDTO investmentPotentialDTO);

    /**
     * Get all the investmentPotentials.
     *
     * @return the list of entities
     */
    List<InvestmentPotentialDTO> findAll();
    /**
     * Get all the InvestmentPotentialDTO where ApplicationProspect is null.
     *
     * @return the list of entities
     */
    List<InvestmentPotentialDTO> findAllWhereApplicationProspectIsNull();


    /**
     * Get the "id" investmentPotential.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InvestmentPotentialDTO> findOne(Long id);

    /**
     * Delete the "id" investmentPotential.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
