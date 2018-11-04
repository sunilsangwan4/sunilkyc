package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.TypeCdDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TypeCd.
 */
public interface TypeCdService {

    /**
     * Save a typeCd.
     *
     * @param typeCdDTO the entity to save
     * @return the persisted entity
     */
    TypeCdDTO save(TypeCdDTO typeCdDTO);

    /**
     * Get all the typeCds.
     *
     * @return the list of entities
     */
    List<TypeCdDTO> findAll();


    /**
     * Get the "id" typeCd.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeCdDTO> findOne(Long id);

    /**
     * Delete the "id" typeCd.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
