package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.EmailVerificationService;
import com.eagle.kyc.domain.EmailVerification;
import com.eagle.kyc.repository.EmailVerificationRepository;
import com.eagle.kyc.service.dto.EmailVerificationDTO;
import com.eagle.kyc.service.mapper.EmailVerificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EmailVerification.
 */
@Service
@Transactional
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final Logger log = LoggerFactory.getLogger(EmailVerificationServiceImpl.class);

    private EmailVerificationRepository emailVerificationRepository;

    private EmailVerificationMapper emailVerificationMapper;

    public EmailVerificationServiceImpl(EmailVerificationRepository emailVerificationRepository, EmailVerificationMapper emailVerificationMapper) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailVerificationMapper = emailVerificationMapper;
    }

    /**
     * Save a emailVerification.
     *
     * @param emailVerificationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailVerificationDTO save(EmailVerificationDTO emailVerificationDTO) {
        log.debug("Request to save EmailVerification : {}", emailVerificationDTO);

        EmailVerification emailVerification = emailVerificationMapper.toEntity(emailVerificationDTO);
        emailVerification = emailVerificationRepository.save(emailVerification);
        return emailVerificationMapper.toDto(emailVerification);
    }

    /**
     * Get all the emailVerifications.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailVerificationDTO> findAll() {
        log.debug("Request to get all EmailVerifications");
        return emailVerificationRepository.findAll().stream()
            .map(emailVerificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one emailVerification by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailVerificationDTO> findOne(Long id) {
        log.debug("Request to get EmailVerification : {}", id);
        return emailVerificationRepository.findById(id)
            .map(emailVerificationMapper::toDto);
    }

    /**
     * Delete the emailVerification by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailVerification : {}", id);
        emailVerificationRepository.deleteById(id);
    }
}
