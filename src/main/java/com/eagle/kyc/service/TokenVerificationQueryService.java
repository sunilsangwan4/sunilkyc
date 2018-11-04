package com.eagle.kyc.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.eagle.kyc.domain.TokenVerification;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.TokenVerificationRepository;
import com.eagle.kyc.service.dto.TokenVerificationCriteria;
import com.eagle.kyc.service.dto.TokenVerificationDTO;
import com.eagle.kyc.service.mapper.TokenVerificationMapper;

/**
 * Service for executing complex queries for TokenVerification entities in the database.
 * The main input is a {@link TokenVerificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TokenVerificationDTO} or a {@link Page} of {@link TokenVerificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TokenVerificationQueryService extends QueryService<TokenVerification> {

    private final Logger log = LoggerFactory.getLogger(TokenVerificationQueryService.class);

    private TokenVerificationRepository tokenVerificationRepository;

    private TokenVerificationMapper tokenVerificationMapper;

    public TokenVerificationQueryService(TokenVerificationRepository tokenVerificationRepository, TokenVerificationMapper tokenVerificationMapper) {
        this.tokenVerificationRepository = tokenVerificationRepository;
        this.tokenVerificationMapper = tokenVerificationMapper;
    }

    /**
     * Return a {@link List} of {@link TokenVerificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TokenVerificationDTO> findByCriteria(TokenVerificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TokenVerification> specification = createSpecification(criteria);
        return tokenVerificationMapper.toDto(tokenVerificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TokenVerificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TokenVerificationDTO> findByCriteria(TokenVerificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TokenVerification> specification = createSpecification(criteria);
        return tokenVerificationRepository.findAll(specification, page)
            .map(tokenVerificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TokenVerificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TokenVerification> specification = createSpecification(criteria);
        return tokenVerificationRepository.count(specification);
    }

    /**
     * Function to convert TokenVerificationCriteria to a {@link Specification}
     */
    private Specification<TokenVerification> createSpecification(TokenVerificationCriteria criteria) {
        Specification<TokenVerification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TokenVerification_.id));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), TokenVerification_.mobileNo));
            }
            if (criteria.getToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToken(), TokenVerification_.token));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TokenVerification_.status));
            }
        }
        return specification;
    }
}
