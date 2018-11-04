package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.BankInformationService;
import com.eagle.kyc.domain.BankInformation;
import com.eagle.kyc.repository.BankInformationRepository;
import com.eagle.kyc.service.dto.BankInformationDTO;
import com.eagle.kyc.service.mapper.BankInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BankInformation.
 */
@Service
@Transactional
public class BankInformationServiceImpl implements BankInformationService {

    private final Logger log = LoggerFactory.getLogger(BankInformationServiceImpl.class);

    private BankInformationRepository bankInformationRepository;

    private BankInformationMapper bankInformationMapper;

    public BankInformationServiceImpl(BankInformationRepository bankInformationRepository, BankInformationMapper bankInformationMapper) {
        this.bankInformationRepository = bankInformationRepository;
        this.bankInformationMapper = bankInformationMapper;
    }

    /**
     * Save a bankInformation.
     *
     * @param bankInformationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BankInformationDTO save(BankInformationDTO bankInformationDTO) {
        log.debug("Request to save BankInformation : {}", bankInformationDTO);

        BankInformation bankInformation = bankInformationMapper.toEntity(bankInformationDTO);
        bankInformation = bankInformationRepository.save(bankInformation);
        return bankInformationMapper.toDto(bankInformation);
    }

    /**
     * Get all the bankInformations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankInformationDTO> findAll() {
        log.debug("Request to get all BankInformations");
        return bankInformationRepository.findAll().stream()
            .map(bankInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bankInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BankInformationDTO> findOne(Long id) {
        log.debug("Request to get BankInformation : {}", id);
        return bankInformationRepository.findById(id)
            .map(bankInformationMapper::toDto);
    }

    /**
     * Delete the bankInformation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankInformation : {}", id);
        bankInformationRepository.deleteById(id);
    }
}
