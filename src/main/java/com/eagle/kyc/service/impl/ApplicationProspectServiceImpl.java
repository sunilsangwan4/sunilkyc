package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.ApplicationProspectService;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.ApplicationProspectRepository;
import com.eagle.kyc.service.dto.ApplicationProspectDTO;
import com.eagle.kyc.service.mapper.ApplicationProspectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ApplicationProspect.
 */
@Service
@Transactional
public class ApplicationProspectServiceImpl implements ApplicationProspectService {

    private final Logger log = LoggerFactory.getLogger(ApplicationProspectServiceImpl.class);

    private ApplicationProspectRepository applicationProspectRepository;

    private ApplicationProspectMapper applicationProspectMapper;

    public ApplicationProspectServiceImpl(ApplicationProspectRepository applicationProspectRepository, ApplicationProspectMapper applicationProspectMapper) {
        this.applicationProspectRepository = applicationProspectRepository;
        this.applicationProspectMapper = applicationProspectMapper;
    }

    /**
     * Save a applicationProspect.
     *
     * @param applicationProspectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationProspectDTO save(ApplicationProspectDTO applicationProspectDTO) {
        log.debug("Request to save ApplicationProspect : {}", applicationProspectDTO);

        ApplicationProspect applicationProspect = applicationProspectMapper.toEntity(applicationProspectDTO);
        applicationProspect = applicationProspectRepository.save(applicationProspect);
        return applicationProspectMapper.toDto(applicationProspect);
    }

    /**
     * Get all the applicationProspects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationProspectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationProspects");
        return applicationProspectRepository.findAll(pageable)
            .map(applicationProspectMapper::toDto);
    }


    /**
     * Get one applicationProspect by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationProspectDTO> findOne(Long id) {
        log.debug("Request to get ApplicationProspect : {}", id);
        return applicationProspectRepository.findById(id)
            .map(applicationProspectMapper::toDto);
    }

    /**
     * Delete the applicationProspect by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationProspect : {}", id);
        applicationProspectRepository.deleteById(id);
    }
}
