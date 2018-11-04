package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.IdentityVerificationService;
import com.eagle.kyc.domain.IdentityVerification;
import com.eagle.kyc.repository.IdentityVerificationRepository;
import com.eagle.kyc.service.dto.IdentityVerificationDTO;
import com.eagle.kyc.service.mapper.IdentityVerificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing IdentityVerification.
 */
@Service
@Transactional
public class IdentityVerificationServiceImpl implements IdentityVerificationService {

    private final Logger log = LoggerFactory.getLogger(IdentityVerificationServiceImpl.class);

    private IdentityVerificationRepository identityVerificationRepository;

    private IdentityVerificationMapper identityVerificationMapper;

    public IdentityVerificationServiceImpl(IdentityVerificationRepository identityVerificationRepository, IdentityVerificationMapper identityVerificationMapper) {
        this.identityVerificationRepository = identityVerificationRepository;
        this.identityVerificationMapper = identityVerificationMapper;
    }

    /**
     * Save a identityVerification.
     *
     * @param identityVerificationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IdentityVerificationDTO save(IdentityVerificationDTO identityVerificationDTO) {
        log.debug("Request to save IdentityVerification : {}", identityVerificationDTO);

        IdentityVerification identityVerification = identityVerificationMapper.toEntity(identityVerificationDTO);
        identityVerification = identityVerificationRepository.save(identityVerification);
        return identityVerificationMapper.toDto(identityVerification);
    }

    /**
     * Get all the identityVerifications.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdentityVerificationDTO> findAll() {
        log.debug("Request to get all IdentityVerifications");
        return identityVerificationRepository.findAll().stream()
            .map(identityVerificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one identityVerification by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IdentityVerificationDTO> findOne(Long id) {
        log.debug("Request to get IdentityVerification : {}", id);
        return identityVerificationRepository.findById(id)
            .map(identityVerificationMapper::toDto);
    }

    /**
     * Delete the identityVerification by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdentityVerification : {}", id);
        identityVerificationRepository.deleteById(id);
    }
}
