package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.NomineeService;
import com.eagle.kyc.domain.Nominee;
import com.eagle.kyc.repository.NomineeRepository;
import com.eagle.kyc.service.dto.NomineeDTO;
import com.eagle.kyc.service.mapper.NomineeMapper;
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
 * Service Implementation for managing Nominee.
 */
@Service
@Transactional
public class NomineeServiceImpl implements NomineeService {

    private final Logger log = LoggerFactory.getLogger(NomineeServiceImpl.class);

    private NomineeRepository nomineeRepository;

    private NomineeMapper nomineeMapper;

    public NomineeServiceImpl(NomineeRepository nomineeRepository, NomineeMapper nomineeMapper) {
        this.nomineeRepository = nomineeRepository;
        this.nomineeMapper = nomineeMapper;
    }

    /**
     * Save a nominee.
     *
     * @param nomineeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NomineeDTO save(NomineeDTO nomineeDTO) {
        log.debug("Request to save Nominee : {}", nomineeDTO);

        Nominee nominee = nomineeMapper.toEntity(nomineeDTO);
        nominee = nomineeRepository.save(nominee);
        return nomineeMapper.toDto(nominee);
    }

    /**
     * Get all the nominees.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NomineeDTO> findAll() {
        log.debug("Request to get all Nominees");
        return nomineeRepository.findAll().stream()
            .map(nomineeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the nominees where ApplicationProspect is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<NomineeDTO> findAllWhereApplicationProspectIsNull() {
        log.debug("Request to get all nominees where ApplicationProspect is null");
        return StreamSupport
            .stream(nomineeRepository.findAll().spliterator(), false)
            .filter(nominee -> nominee.getApplicationProspect() == null)
            .map(nomineeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one nominee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NomineeDTO> findOne(Long id) {
        log.debug("Request to get Nominee : {}", id);
        return nomineeRepository.findById(id)
            .map(nomineeMapper::toDto);
    }

    /**
     * Delete the nominee by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nominee : {}", id);
        nomineeRepository.deleteById(id);
    }
}
