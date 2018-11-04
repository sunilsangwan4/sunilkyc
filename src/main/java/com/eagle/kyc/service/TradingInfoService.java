package com.eagle.kyc.service;

import com.eagle.kyc.service.dto.TradingInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TradingInfo.
 */
public interface TradingInfoService {

    /**
     * Save a tradingInfo.
     *
     * @param tradingInfoDTO the entity to save
     * @return the persisted entity
     */
    TradingInfoDTO save(TradingInfoDTO tradingInfoDTO);

    /**
     * Get all the tradingInfos.
     *
     * @return the list of entities
     */
    List<TradingInfoDTO> findAll();
    /**
     * Get all the TradingInfoDTO where ApplicationProspect is null.
     *
     * @return the list of entities
     */
    List<TradingInfoDTO> findAllWhereApplicationProspectIsNull();


    /**
     * Get the "id" tradingInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TradingInfoDTO> findOne(Long id);

    /**
     * Delete the "id" tradingInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
