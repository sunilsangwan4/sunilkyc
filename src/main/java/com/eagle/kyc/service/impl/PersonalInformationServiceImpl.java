package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.PersonalInformationService;
import com.eagle.kyc.domain.PersonalInformation;
import com.eagle.kyc.repository.PersonalInformationRepository;
import com.eagle.kyc.service.dto.PersonalInformationDTO;
import com.eagle.kyc.service.mapper.PersonalInformationMapper;
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
 * Service Implementation for managing PersonalInformation.
 */
@Service
@Transactional
public class PersonalInformationServiceImpl implements PersonalInformationService {

    private final Logger log = LoggerFactory.getLogger(PersonalInformationServiceImpl.class);

    private PersonalInformationRepository personalInformationRepository;

    private PersonalInformationMapper personalInformationMapper;

    public PersonalInformationServiceImpl(PersonalInformationRepository personalInformationRepository, PersonalInformationMapper personalInformationMapper) {
        this.personalInformationRepository = personalInformationRepository;
        this.personalInformationMapper = personalInformationMapper;
    }

    /**
     * Save a personalInformation.
     *
     * @param personalInformationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonalInformationDTO save(PersonalInformationDTO personalInformationDTO) {
        log.debug("Request to save PersonalInformation : {}", personalInformationDTO);

        PersonalInformation personalInformation = personalInformationMapper.toEntity(personalInformationDTO);
        personalInformation = personalInformationRepository.save(personalInformation);
        return personalInformationMapper.toDto(personalInformation);
    }

    /**
     * Get all the personalInformations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonalInformationDTO> findAll() {
        log.debug("Request to get all PersonalInformations");
        return personalInformationRepository.findAll().stream()
            .map(personalInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the personalInformations where ApplicationProspect is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PersonalInformationDTO> findAllWhereApplicationProspectIsNull() {
        log.debug("Request to get all personalInformations where ApplicationProspect is null");
        return StreamSupport
            .stream(personalInformationRepository.findAll().spliterator(), false)
            .filter(personalInformation -> personalInformation.getApplicationProspect() == null)
            .map(personalInformationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one personalInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalInformationDTO> findOne(Long id) {
        log.debug("Request to get PersonalInformation : {}", id);
        return personalInformationRepository.findById(id)
            .map(personalInformationMapper::toDto);
    }

    /**
     * Delete the personalInformation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalInformation : {}", id);
        personalInformationRepository.deleteById(id);
    }
}
