package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.InvestmentPotentialService;
import com.eagle.kyc.domain.InvestmentPotential;
import com.eagle.kyc.repository.InvestmentPotentialRepository;
import com.eagle.kyc.service.dto.InvestmentPotentialDTO;
import com.eagle.kyc.service.mapper.InvestmentPotentialMapper;
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
 * Service Implementation for managing InvestmentPotential.
 */
@Service
@Transactional
public class InvestmentPotentialServiceImpl implements InvestmentPotentialService {

    private final Logger log = LoggerFactory.getLogger(InvestmentPotentialServiceImpl.class);

    private InvestmentPotentialRepository investmentPotentialRepository;

    private InvestmentPotentialMapper investmentPotentialMapper;

    public InvestmentPotentialServiceImpl(InvestmentPotentialRepository investmentPotentialRepository, InvestmentPotentialMapper investmentPotentialMapper) {
        this.investmentPotentialRepository = investmentPotentialRepository;
        this.investmentPotentialMapper = investmentPotentialMapper;
    }

    /**
     * Save a investmentPotential.
     *
     * @param investmentPotentialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InvestmentPotentialDTO save(InvestmentPotentialDTO investmentPotentialDTO) {
        log.debug("Request to save InvestmentPotential : {}", investmentPotentialDTO);

        InvestmentPotential investmentPotential = investmentPotentialMapper.toEntity(investmentPotentialDTO);
        investmentPotential = investmentPotentialRepository.save(investmentPotential);
        return investmentPotentialMapper.toDto(investmentPotential);
    }

    /**
     * Get all the investmentPotentials.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvestmentPotentialDTO> findAll() {
        log.debug("Request to get all InvestmentPotentials");
        return investmentPotentialRepository.findAll().stream()
            .map(investmentPotentialMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the investmentPotentials where ApplicationProspect is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InvestmentPotentialDTO> findAllWhereApplicationProspectIsNull() {
        log.debug("Request to get all investmentPotentials where ApplicationProspect is null");
        return StreamSupport
            .stream(investmentPotentialRepository.findAll().spliterator(), false)
            .filter(investmentPotential -> investmentPotential.getApplicationProspect() == null)
            .map(investmentPotentialMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one investmentPotential by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvestmentPotentialDTO> findOne(Long id) {
        log.debug("Request to get InvestmentPotential : {}", id);
        return investmentPotentialRepository.findById(id)
            .map(investmentPotentialMapper::toDto);
    }

    /**
     * Delete the investmentPotential by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvestmentPotential : {}", id);
        investmentPotentialRepository.deleteById(id);
    }
}
