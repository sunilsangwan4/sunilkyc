package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.DepositoryInfoService;
import com.eagle.kyc.domain.DepositoryInfo;
import com.eagle.kyc.repository.DepositoryInfoRepository;
import com.eagle.kyc.service.dto.DepositoryInfoDTO;
import com.eagle.kyc.service.mapper.DepositoryInfoMapper;
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
 * Service Implementation for managing DepositoryInfo.
 */
@Service
@Transactional
public class DepositoryInfoServiceImpl implements DepositoryInfoService {

    private final Logger log = LoggerFactory.getLogger(DepositoryInfoServiceImpl.class);

    private DepositoryInfoRepository depositoryInfoRepository;

    private DepositoryInfoMapper depositoryInfoMapper;

    public DepositoryInfoServiceImpl(DepositoryInfoRepository depositoryInfoRepository, DepositoryInfoMapper depositoryInfoMapper) {
        this.depositoryInfoRepository = depositoryInfoRepository;
        this.depositoryInfoMapper = depositoryInfoMapper;
    }

    /**
     * Save a depositoryInfo.
     *
     * @param depositoryInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DepositoryInfoDTO save(DepositoryInfoDTO depositoryInfoDTO) {
        log.debug("Request to save DepositoryInfo : {}", depositoryInfoDTO);

        DepositoryInfo depositoryInfo = depositoryInfoMapper.toEntity(depositoryInfoDTO);
        depositoryInfo = depositoryInfoRepository.save(depositoryInfo);
        return depositoryInfoMapper.toDto(depositoryInfo);
    }

    /**
     * Get all the depositoryInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DepositoryInfoDTO> findAll() {
        log.debug("Request to get all DepositoryInfos");
        return depositoryInfoRepository.findAll().stream()
            .map(depositoryInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the depositoryInfos where ApplicationProspect is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DepositoryInfoDTO> findAllWhereApplicationProspectIsNull() {
        log.debug("Request to get all depositoryInfos where ApplicationProspect is null");
        return StreamSupport
            .stream(depositoryInfoRepository.findAll().spliterator(), false)
            .filter(depositoryInfo -> depositoryInfo.getApplicationProspect() == null)
            .map(depositoryInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one depositoryInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DepositoryInfoDTO> findOne(Long id) {
        log.debug("Request to get DepositoryInfo : {}", id);
        return depositoryInfoRepository.findById(id)
            .map(depositoryInfoMapper::toDto);
    }

    /**
     * Delete the depositoryInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DepositoryInfo : {}", id);
        depositoryInfoRepository.deleteById(id);
    }
}
