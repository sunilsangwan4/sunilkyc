package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.TradingInfoService;
import com.eagle.kyc.domain.TradingInfo;
import com.eagle.kyc.repository.TradingInfoRepository;
import com.eagle.kyc.service.dto.TradingInfoDTO;
import com.eagle.kyc.service.mapper.TradingInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing TradingInfo.
 */
@Service
@Transactional
public class TradingInfoServiceImpl implements TradingInfoService {

    private final Logger log = LoggerFactory.getLogger(TradingInfoServiceImpl.class);

    private TradingInfoRepository tradingInfoRepository;

    private TradingInfoMapper tradingInfoMapper;

    public TradingInfoServiceImpl(TradingInfoRepository tradingInfoRepository, TradingInfoMapper tradingInfoMapper) {
        this.tradingInfoRepository = tradingInfoRepository;
        this.tradingInfoMapper = tradingInfoMapper;
    }

    /**
     * Save a tradingInfo.
     *
     * @param tradingInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TradingInfoDTO save(TradingInfoDTO tradingInfoDTO) {
        log.debug("Request to save TradingInfo : {}", tradingInfoDTO);

        TradingInfo tradingInfo = tradingInfoMapper.toEntity(tradingInfoDTO);
        tradingInfo = tradingInfoRepository.save(tradingInfo);
        return tradingInfoMapper.toDto(tradingInfo);
    }

    /**
     * Get all the tradingInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TradingInfoDTO> findAll() {
        log.debug("Request to get all TradingInfos");
        return tradingInfoRepository.findAll().stream()
            .map(tradingInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the tradingInfos where ApplicationProspect is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TradingInfoDTO> findAllWhereApplicationProspectIsNull() {
        log.debug("Request to get all tradingInfos where ApplicationProspect is null");
        return StreamSupport
            .stream(tradingInfoRepository.findAll().spliterator(), false)
            .filter(tradingInfo -> tradingInfo.getApplicationProspect() == null)
            .map(tradingInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tradingInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TradingInfoDTO> findOne(Long id) {
        log.debug("Request to get TradingInfo : {}", id);
        return tradingInfoRepository.findById(id)
            .map(tradingInfoMapper::toDto);
    }

    /**
     * Delete the tradingInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TradingInfo : {}", id);
        tradingInfoRepository.deleteById(id);
    }
}
