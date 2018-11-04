package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.PersonalInformationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PersonalInformation.
 */
public interface PersonalInformationService {

    /**
     * Save a personalInformation.
     *
     * @param personalInformationDTO the entity to save
     * @return the persisted entity
     */
    PersonalInformationDTO save(PersonalInformationDTO personalInformationDTO);

    /**
     * Get all the personalInformations.
     *
     * @return the list of entities
     */
    List<PersonalInformationDTO> findAll();
    /**
     * Get all the PersonalInformationDTO where ApplicationProspect is null.
     *
     * @return the list of entities
     */
    List<PersonalInformationDTO> findAllWhereApplicationProspectIsNull();


    /**
     * Get the "id" personalInformation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonalInformationDTO> findOne(Long id);

    /**
     * Delete the "id" personalInformation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
