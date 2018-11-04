package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.TokenVerificationService;
import com.eagle.kyc.domain.TokenVerification;
import com.eagle.kyc.repository.TokenVerificationRepository;
import com.eagle.kyc.service.dto.TokenVerificationDTO;
import com.eagle.kyc.service.mapper.TokenVerificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TokenVerification.
 */
@Service
@Transactional
public class TokenVerificationServiceImpl implements TokenVerificationService {

    private final Logger log = LoggerFactory.getLogger(TokenVerificationServiceImpl.class);

    private TokenVerificationRepository tokenVerificationRepository;

    private TokenVerificationMapper tokenVerificationMapper;

    public TokenVerificationServiceImpl(TokenVerificationRepository tokenVerificationRepository, TokenVerificationMapper tokenVerificationMapper) {
        this.tokenVerificationRepository = tokenVerificationRepository;
        this.tokenVerificationMapper = tokenVerificationMapper;
    }

    /**
     * Save a tokenVerification.
     *
     * @param tokenVerificationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TokenVerificationDTO save(TokenVerificationDTO tokenVerificationDTO) {
        log.debug("Request to save TokenVerification : {}", tokenVerificationDTO);

        TokenVerification tokenVerification = tokenVerificationMapper.toEntity(tokenVerificationDTO);
        tokenVerification = tokenVerificationRepository.save(tokenVerification);
        return tokenVerificationMapper.toDto(tokenVerification);
    }

    /**
     * Get all the tokenVerifications.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TokenVerificationDTO> findAll() {
        log.debug("Request to get all TokenVerifications");
        return tokenVerificationRepository.findAll().stream()
            .map(tokenVerificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tokenVerification by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TokenVerificationDTO> findOne(Long id) {
        log.debug("Request to get TokenVerification : {}", id);
        return tokenVerificationRepository.findById(id)
            .map(tokenVerificationMapper::toDto);
    }

    /**
     * Delete the tokenVerification by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TokenVerification : {}", id);
        tokenVerificationRepository.deleteById(id);
    }
}
